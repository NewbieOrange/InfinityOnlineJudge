package xyz.chengzi.ooad.controller

import io.javalin.http.Context
import io.javalin.http.UnauthorizedResponse
import xyz.chengzi.ooad.repository.user.UsernameSpecification
import xyz.chengzi.ooad.server.ApplicationServer

class SessionController(server: ApplicationServer) : AbstractController(server) {
    fun login(ctx: Context) {
        val userRepository = repositoryService.createUserRepository()
        val basicAuthCredentials = ctx.basicAuthCredentials()
        userRepository.use {
            val user = it.find(UsernameSpecification(basicAuthCredentials.username))
            if (user != null && sessionService.checkPassword(user, basicAuthCredentials.password)) {
                ctx.cookie("token", String(sessionService.generateToken(user)))
                return
            }
        }
        throw UnauthorizedResponse()
    }

    fun logout(ctx: Context) {
        val userRepository = repositoryService.createUserRepository()
        sessionService.invalidateToken(userRepository.use {
            getCallerUser(it, ctx)
        })
        ctx.clearCookieStore()
    }
}
