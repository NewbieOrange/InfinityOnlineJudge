package xyz.chengzi.ooad.controller

import com.google.common.collect.ImmutableMap
import com.google.common.collect.Maps
import io.javalin.http.Context
import io.javalin.http.UnauthorizedResponse
import org.json.JSONObject
import xyz.chengzi.ooad.dto.JudgeRequestMessage
import xyz.chengzi.ooad.embeddable.SubmissionStatus
import xyz.chengzi.ooad.entity.Submission
import xyz.chengzi.ooad.repository.SinceIdSpecification
import xyz.chengzi.ooad.server.ApplicationServer
import xyz.chengzi.ooad.service.RabbitMQService
import xyz.chengzi.ooad.util.MapperUtil
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class SubmissionController(server: ApplicationServer) : AbstractController(server) {
    private val extensionNames = ImmutableMap.of("gcc", ".c", "g++", ".cpp", "java", ".java", "python", ".py")
    private val rabbitMQService = RabbitMQService()

    fun create(ctx: Context) {
        val caller = getCallerUser(ctx) ?: throw UnauthorizedResponse()
        val requestBody = JSONObject(ctx.body())
        val codeContent = requestBody.getString("code")
        val item = Submission()
        item.problem = repositoryService.problemRepository.findById(requestBody.getInt("problem"))
        item.user = caller
        item.status = SubmissionStatus.PENDING
        item.timeUsage = 0
        item.memoryUsage = 0
        item.language = requestBody.getString("language")
        item.codeLength = codeContent.length
        item.timestamp = Date()
        repositoryService.submissionRepository.add(item)

        val path = Paths.get("/home/jilao/submissions/" + item.id + extensionNames[item.language])
        Files.createFile(path)
        Files.writeString(path, codeContent)
        val result = rabbitMQService.send(MapperUtil.writeValueAsString(JudgeRequestMessage(item.id, item.problem.id, item.language, item.problem.timeLimit, item.problem.memoryLimit, false)))
        ctx.result(result)
    }

    fun listAll(ctx: Context) {
        val since = ctx.queryParam("since", "0")!!.toInt()
        val items = repositoryService.submissionRepository.findAll(SinceIdSpecification(since, 10))
        ctx.json(items)
    }
}
