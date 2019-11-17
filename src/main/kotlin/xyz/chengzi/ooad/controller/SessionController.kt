package xyz.chengzi.ooad.controller

import io.javalin.http.Context
import io.javalin.http.UnauthorizedResponse
import xyz.chengzi.ooad.exception.EntityNotFoundException
import xyz.chengzi.ooad.server.ApplicationServer

class SessionController(server: ApplicationServer) : AbstractController(server) {
    private val userRepository = server.repositoryService.userRepository

    fun login(ctx: Context) {
        val basicAuthCredentials = ctx.basicAuthCredentials()
        try {
            val user = userRepository.findByUsername(basicAuthCredentials.username)
            if (sessionService.checkPassword(user, basicAuthCredentials.password)) {
                ctx.cookie("token", String(sessionService.generateToken(user)))
                return
            }
        } catch (e: EntityNotFoundException) {
        }
        throw UnauthorizedResponse()
    }

    fun logout(ctx: Context) {
        sessionService.invalidateToken(getCallerUser(ctx))
        ctx.clearCookieStore()
    }
}
