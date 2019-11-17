package xyz.chengzi.ooad.controller

import io.javalin.http.Context
import xyz.chengzi.ooad.entity.User
import xyz.chengzi.ooad.server.ApplicationServer

abstract class AbstractController(server: ApplicationServer) {
    protected val sessionService = server.sessionService!!
    protected val repositoryService = server.repositoryService!!

    /**
     * Get the caller user of the ctx, null if the caller is not authorized.
     *
     * @param ctx the context.
     * @return the caller user, null if the caller is not authorized.
     */
    fun getCallerUser(ctx: Context): User? {
        val token = ctx.cookie("token")
        return if (token == null) null else sessionService.findTokenOwner(token.toByteArray())
    }
}
