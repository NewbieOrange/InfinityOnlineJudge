package xyz.chengzi.ooad.controller

import io.javalin.http.Context
import io.javalin.http.UnauthorizedResponse
import org.json.JSONObject
import xyz.chengzi.ooad.entity.User
import xyz.chengzi.ooad.server.ApplicationServer

class UserController(server: ApplicationServer) : AbstractController(server) {
    private val userRepository = repositoryService.userRepository

    fun create(ctx: Context) {
        val caller = getCallerUser(ctx) ?: throw UnauthorizedResponse()
        if (!caller.hasPermission("users.create")) {
            throw UnauthorizedResponse()
        }
        val body = JSONObject(ctx.body())
        val user = User(body.getString("username"), sessionService.hashPassword(body.getString("password")))
        userRepository.add(user)
    }

    fun getCurrentUser(ctx: Context) {
        val caller = getCallerUser(ctx) ?: throw UnauthorizedResponse()
        ctx.json(caller)
    }
}
