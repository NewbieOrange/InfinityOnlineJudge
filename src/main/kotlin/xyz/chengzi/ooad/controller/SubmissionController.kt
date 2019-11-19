package xyz.chengzi.ooad.controller

import io.javalin.http.Context
import io.javalin.http.UnauthorizedResponse
import org.json.JSONObject
import xyz.chengzi.ooad.embeddable.SubmissionStatus
import xyz.chengzi.ooad.entity.Submission
import xyz.chengzi.ooad.repository.SinceIdSpecification
import xyz.chengzi.ooad.server.ApplicationServer
import java.util.*

class SubmissionController(server: ApplicationServer) : AbstractController(server) {
    fun create(ctx: Context) {
        val caller = getCallerUser(ctx) ?: throw UnauthorizedResponse()
        val requestBody = JSONObject(ctx.body())
        val item = Submission()
        item.problem = repositoryService.problemRepository.findById(requestBody.getInt("problem"))
        item.user = caller
        item.codeContent = requestBody.getString("code")
        item.status = SubmissionStatus.PENDING
        item.timeUsage = 0
        item.memoryUsage = 0
        item.language = requestBody.getString("language")
        item.codeLength = item.codeContent.length
        item.timestamp = Date()
        repositoryService.submissionRepository.add(item)
    }

    fun listAll(ctx: Context) {
        val since = ctx.queryParam("since", "0")!!.toInt()
        val items = repositoryService.submissionRepository.findAll(SinceIdSpecification(since, 10))
        ctx.json(items)
    }
}
