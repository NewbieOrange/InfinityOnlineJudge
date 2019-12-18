package xyz.chengzi.ooad.controller

import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import org.json.JSONObject
import xyz.chengzi.ooad.dto.ProblemResponse
import xyz.chengzi.ooad.dto.SubmissionResponse
import xyz.chengzi.ooad.embeddable.SubmissionStatus
import xyz.chengzi.ooad.entity.Problem
import xyz.chengzi.ooad.repository.EmptyGroups
import xyz.chengzi.ooad.repository.entity.SinceIdSpecification
import xyz.chengzi.ooad.repository.submission.SubmissionByTimeMemoryOrder
import xyz.chengzi.ooad.repository.submission.SubmissionFromProblemSpecification
import xyz.chengzi.ooad.repository.submission.SubmissionStatusSpecification
import xyz.chengzi.ooad.server.ApplicationServer
import java.nio.file.Files
import java.nio.file.NoSuchFileException
import java.nio.file.Path
import java.util.stream.Collectors

class ProblemController(server: ApplicationServer) : AbstractController(server) {
    fun create(ctx: Context) {
        val problemRepository = repositoryService.createProblemRepository()
        val requestBody = JSONObject(ctx.body())
        val item = Problem()
        item.title = requestBody.getString("title")
        item.description = requestBody.getString("description")
        item.descriptionHtml = requestBody.getString("descriptionHtml")
        item.type = requestBody.getString("type")
        item.isSpecial = requestBody.getBoolean("special")
        item.timeLimit = requestBody.getInt("timeLimit")
        item.memoryLimit = requestBody.getInt("memoryLimit")
        item.acceptedAmount = 0
        item.submissionAmount = 0
        problemRepository.use {
            it.add(item)
        }
    }

    fun update(ctx: Context) {
        val problemRepository = repositoryService.createProblemRepository()
        val requestBody = JSONObject(ctx.body())
        problemRepository.use {
            val item = it.findById(ctx.pathParam("id", Int::class.java).get()) ?: throw NotFoundResponse()
            item.title = requestBody.getString("title")
            item.description = requestBody.getString("description")
            item.descriptionHtml = requestBody.getString("descriptionHtml")
            item.type = requestBody.getString("type")
            item.isSpecial = requestBody.getBoolean("special")
            problemRepository.update(item)
        }
    }

    fun remove(ctx: Context) {
        val problemRepository = repositoryService.createProblemRepository()
        problemRepository.use {
            it.remove(it.findById(ctx.pathParam("id", Int::class.java).get()) ?: throw NotFoundResponse())
        }
    }

    fun listAll(ctx: Context) {
        val problemRepository = repositoryService.createProblemRepository()
        val since = ctx.queryParam("since", "0")!!.toInt()
        problemRepository.use { repo ->
            val items = repo.findAll(SinceIdSpecification(since), 10)
            ctx.json(items.map { ProblemResponse(it) }.toList())
        }
    }

    fun getById(ctx: Context) {
        val problemRepository = repositoryService.createProblemRepository()
        problemRepository.use {
            ctx.json(ProblemResponse(it.findById(ctx.pathParam("id", Int::class.java).get())
                    ?: throw NotFoundResponse()))
        }
    }

    fun listSubmissionRank(ctx: Context) {
        val problemRepository = repositoryService.createProblemRepository()
        val id = ctx.pathParam("id", Int::class.java).get()
        val since = ctx.queryParam("since", "0")!!.toInt()

        problemRepository.use { problemRepo ->
            val problem = problemRepo.findById(id)!!
            val items = problem.rankList
            ctx.json(items.map { SubmissionResponse(it.value) })
        }
    }

    fun createFile(ctx: Context) {
        val id = ctx.pathParam("id")
        val fileName = ctx.pathParam("fileName")
        val path = Path.of("./problems/$id/$fileName")
        Files.createDirectories(path.parent)
        Files.write(path, ctx.bodyAsBytes())
    }

    fun getFile(ctx: Context) {
        val id = ctx.pathParam("id")
        val fileName = ctx.pathParam("fileName")
        val path = Path.of("./problems/$id/$fileName")
        if (Files.notExists(path)) {
            throw NotFoundResponse()
        }
        ctx.result(path.toFile().inputStream())
    }

    fun deleteFile(ctx: Context) {
        val id = ctx.pathParam("id")
        val fileName = ctx.pathParam("fileName")
        val path = Path.of("./problems/$id/$fileName")
        Files.deleteIfExists(path)
    }

    fun listFiles(ctx: Context) {
        val id = ctx.pathParam("id")
        val path = Path.of("./problems/$id/")
        try {
            ctx.result(Files.list(path).map(Path::getFileName).collect(Collectors.toList()).toString())
        } catch (e: NoSuchFileException) {
            ctx.result("[]")
        }
    }
}
