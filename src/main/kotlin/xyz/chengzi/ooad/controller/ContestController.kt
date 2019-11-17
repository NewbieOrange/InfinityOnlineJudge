package xyz.chengzi.ooad.controller

import io.javalin.http.Context
import xyz.chengzi.ooad.server.ApplicationServer

class ContestController(server: ApplicationServer) : AbstractController(server) {
    fun listContests(ctx: Context) {
        val since = ctx.queryParam("since", "-1")
    }
}
