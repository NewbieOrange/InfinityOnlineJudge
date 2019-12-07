package xyz.chengzi.ooad.controller

import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import io.javalin.http.UnauthorizedResponse
import org.json.JSONArray
import org.json.JSONObject
import xyz.chengzi.ooad.dto.UserResponse
import xyz.chengzi.ooad.entity.User
import xyz.chengzi.ooad.repository.AndSpecification
import xyz.chengzi.ooad.repository.EmptySpecification
import xyz.chengzi.ooad.repository.JpqlSpecification
import xyz.chengzi.ooad.repository.entity.SinceIdSpecification
import xyz.chengzi.ooad.repository.user.PermissionSpecification
import xyz.chengzi.ooad.repository.user.UsernameSpecification
import xyz.chengzi.ooad.server.ApplicationServer
import java.util.stream.Collectors

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

    fun getById(ctx: Context) {
        val id = ctx.pathParam("id", Int::class.java).get()
        ctx.json(UserResponse(userRepository.findById(id) ?: throw NotFoundResponse()))
    }

    fun listAll(ctx: Context) {
        val since = ctx.queryParam("since", "0")!!.toInt()
        val items = userRepository.findAll(SinceIdSpecification(since), 10)
        ctx.json(items.map { UserResponse(it) }.toList())
    }

    fun search(ctx: Context) {
        val username = ctx.queryParam("username")
        val permission = ctx.queryParam("permission")
        if (username == null && permission == null) {
            throw BadRequestResponse()
        }

        var specification: JpqlSpecification<User> = EmptySpecification()
        if (username != null) {
            specification = AndSpecification(specification, UsernameSpecification(username))
        }
        if (permission != null) {
            specification = AndSpecification(specification, PermissionSpecification(permission))
        }
        ctx.json(userRepository.findAll(specification).map { UserResponse(it) }.toList())
    }

    fun setPermissions(ctx: Context) {
        val user = userRepository.findById(ctx.pathParam("id", Int::class.java).get()) ?: throw NotFoundResponse()
        val permissions = JSONArray(ctx.body()).map { it as String }
        user.permissions = permissions
        userRepository.update(user)
    }

    fun getPermissions(ctx: Context) {
        val id = ctx.pathParam("id", Int::class.java).get()
        ctx.json((userRepository.findById(id) ?: throw NotFoundResponse()).permissions)
    }

    fun getCurrentUser(ctx: Context) {
        val caller = getCallerUser(ctx) ?: throw UnauthorizedResponse()
        ctx.json(UserResponse(caller))
    }
}
