package xyz.chengzi.ooad.controller

import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import org.json.JSONObject
import xyz.chengzi.ooad.entity.Problem
import xyz.chengzi.ooad.repository.SinceIdSpecification
import xyz.chengzi.ooad.server.ApplicationServer
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.NoSuchFileException
import java.nio.file.Path
import java.util.stream.Collectors

class ProblemController(server: ApplicationServer) : AbstractController(server) {
    private val problemRepository = repositoryService.problemRepository

    fun create(ctx: Context) {
        val requestBody = JSONObject(ctx.body())
        val item = Problem()
        item.title = requestBody.getString("title")
        item.description = requestBody.getString("description")
        item.descriptionHtml = requestBody.getString("descriptionHtml")
        item.type = requestBody.getString("type")
        item.isSpecial = requestBody.getBoolean("special")
        item.acceptedAmount = 0
        item.submissionAmount = 0
        problemRepository.add(item)
    }

    fun update(ctx: Context) {
        val requestBody = JSONObject(ctx.body())
        val item = repositoryService.problemRepository.findById(ctx.pathParam("id", Int::class.java).get())
        item.title = requestBody.getString("title")
        item.description = requestBody.getString("description")
        item.descriptionHtml = requestBody.getString("descriptionHtml")
        item.type = requestBody.getString("type")
        item.isSpecial = requestBody.getBoolean("special")
        problemRepository.update(item)
    }

    fun remove(ctx: Context) {
        problemRepository.remove(problemRepository.findById(ctx.pathParam("id", Int::class.java).get()))
    }

    fun listAll(ctx: Context) {
        val since = ctx.queryParam("since", "0")!!.toInt()
        val items = repositoryService.problemRepository.findAll(SinceIdSpecification(since, 10))
        ctx.json(items)
    }

    fun getById(ctx: Context) {
        val item = repositoryService.problemRepository.findById(ctx.pathParam("id", Int::class.java).get())
        ctx.json(item)
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
