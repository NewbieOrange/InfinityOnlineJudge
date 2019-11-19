package xyz.chengzi.ooad.controller

import io.javalin.http.Context
import org.json.JSONObject
import xyz.chengzi.ooad.entity.Problem
import xyz.chengzi.ooad.repository.SinceIdSpecification
import xyz.chengzi.ooad.server.ApplicationServer

class ProblemController(server: ApplicationServer) : AbstractController(server) {
    fun create(ctx: Context) {
        val requestBody = JSONObject(ctx.body())
        val item = Problem()
        item.title = requestBody.getString("title")
        item.description = requestBody.getString("description")
        item.descriptionHtml = requestBody.getString("descriptionHtml")
        item.acceptedAmount = 0
        item.submissionAmount = 0
        repositoryService.problemRepository.add(item)
    }

    fun listAll(ctx: Context) {
        val since = ctx.queryParam("since", "0")!!.toInt()
        val items = repositoryService.problemRepository.findAll(SinceIdSpecification(since, 10))
        ctx.json(items)
    }
}
