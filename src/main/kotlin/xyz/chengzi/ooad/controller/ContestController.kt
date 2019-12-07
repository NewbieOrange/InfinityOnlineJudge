package xyz.chengzi.ooad.controller

import io.javalin.http.Context
import xyz.chengzi.ooad.repository.entity.SinceIdSpecification
import xyz.chengzi.ooad.server.ApplicationServer

class ContestController(server: ApplicationServer) : AbstractController(server) {
    fun create(ctx: Context) {
    }

    fun listAll(ctx: Context) {
        val since = ctx.queryParam("since", "0")!!.toInt()
        val contests = repositoryService.contestRepository.findAll(SinceIdSpecification(since), 10)
        ctx.json(contests)
    }
}
