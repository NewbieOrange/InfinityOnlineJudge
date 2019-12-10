package xyz.chengzi.ooad.controller

import com.google.common.collect.ImmutableMap
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import io.javalin.http.UnauthorizedResponse
import org.json.JSONObject
import xyz.chengzi.ooad.dto.JudgeRequestMessage
import xyz.chengzi.ooad.dto.JudgeResponseList
import xyz.chengzi.ooad.dto.ProblemResponse
import xyz.chengzi.ooad.dto.SubmissionResponse
import xyz.chengzi.ooad.embeddable.SubmissionCase
import xyz.chengzi.ooad.embeddable.SubmissionStatus
import xyz.chengzi.ooad.entity.Submission
import xyz.chengzi.ooad.repository.entity.SinceIdSpecification
import xyz.chengzi.ooad.server.ApplicationServer
import xyz.chengzi.ooad.service.RabbitMQService
import xyz.chengzi.ooad.util.MapperUtil
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.function.Consumer
import java.util.logging.Logger
import kotlin.collections.ArrayList

class SubmissionController(server: ApplicationServer) : AbstractController(server) {
    private val extensionNames = ImmutableMap.of("gcc", ".c", "g++", ".cpp", "java", ".java", "python", ".py")
    private val rabbitMQService = RabbitMQService()

    fun create(ctx: Context) {
        val problemRepository = repositoryService.createProblemRepository()
        val submissionRepository = repositoryService.createSubmissionRepository()
        val requestBody = JSONObject(ctx.body())
        val caller = getCallerUser(ctx) ?: throw UnauthorizedResponse()
        val codeContent = requestBody.getString("code")
        val item = Submission()
        problemRepository.use {
            item.problem = it.findById(requestBody.getInt("problem"))
        }
        submissionRepository.use {
            item.user = caller
            item.status = SubmissionStatus.PENDING
            item.cases = ArrayList()
            item.language = requestBody.getString("language")
            item.codeLength = codeContent.length
            item.timestamp = Date()
            it.add(item)

            val path = Paths.get("./submissions/${item.id}${extensionNames[item.language]}")
            Files.createFile(path)
            Files.writeString(path, codeContent)
            rabbitMQService.send(MapperUtil.writeValueAsString(JudgeRequestMessage(item.id, item.problem.id, item.language, item.problem.timeLimit, item.problem.memoryLimit, item.problem.isSpecial))) { submissionCallback(it) }
            ctx.result(item.id.toString())
        }
    }

    private fun submissionCallback(str: String) {
        val submissionRepository = repositoryService.createSubmissionRepository()
        val responseList = MapperUtil.readValue(str, JudgeResponseList::class.java)
        submissionRepository.use { repo ->
            val submission = repo.findById(responseList.id)!!
            submission.status = SubmissionStatus.valueOf(responseList.status)
            submission.cases = responseList.cases.map {
                val case = SubmissionCase()
                case.id = it.id
                case.status = SubmissionStatus.valueOf(it.status)
                case.timeUsage = it.time
                case.memoryUsage = it.memory
                case
            }
            repo.update(submission)
        }
    }

    fun getById(ctx: Context) {
        val submissionRepository = repositoryService.createSubmissionRepository()
        submissionRepository.use {
            ctx.json(SubmissionResponse(it.findById(ctx.pathParam("id", Int::class.java).get())
                    ?: throw NotFoundResponse()))
        }
    }

    fun listAll(ctx: Context) {
        val submissionRepository = repositoryService.createSubmissionRepository()
        val since = ctx.queryParam("since", "0")!!.toInt()
        submissionRepository.use {
            val items = it.findAll(SinceIdSpecification(since), 10)
            ctx.json(items.map { SubmissionResponse(it) }.toList())
        }
    }
}
