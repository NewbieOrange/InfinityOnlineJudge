package xyz.chengzi.ooad.controller

import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import io.javalin.http.UnauthorizedResponse
import org.json.JSONArray
import org.json.JSONObject
import xyz.chengzi.ooad.dto.UserResponse
import xyz.chengzi.ooad.entity.User
import xyz.chengzi.ooad.repository.Specification
import xyz.chengzi.ooad.repository.entity.SinceIdSpecification
import xyz.chengzi.ooad.repository.user.PermissionSpecification
import xyz.chengzi.ooad.repository.user.UsernameSpecification
import xyz.chengzi.ooad.server.ApplicationServer

class UserController(server: ApplicationServer) : AbstractController(server) {
    fun create(ctx: Context) {
        val userRepository = repositoryService.createUserRepository()
        val caller = getCallerUser(ctx) ?: throw UnauthorizedResponse()
        if (!checkPermission(caller, "users.create")) {
            throw UnauthorizedResponse()
        }
        val body = JSONObject(ctx.body())
        val user = User(body.getString("username"), sessionService.hashPassword(body.getString("password")))
        userRepository.use {
            it.add(user)
        }
    }

    fun getById(ctx: Context) {
        val userRepository = repositoryService.createUserRepository()
        val id = ctx.pathParam("id", Int::class.java).get()
        userRepository.use {
            ctx.json(UserResponse(it.findById(id) ?: throw NotFoundResponse()))
        }
    }

    fun listAll(ctx: Context) {
        val userRepository = repositoryService.createUserRepository()
        val since = ctx.queryParam("since", "0")!!.toInt()
        val username = ctx.queryParam("username")
        val permission = ctx.queryParam("permission")

        var specification: Specification<User> = SinceIdSpecification(since)
        if (username != null) {
            specification = specification.and(UsernameSpecification(username))
        }
        if (permission != null) {
            specification = specification.and(PermissionSpecification(permission))
        }
        userRepository.use { repo ->
            ctx.json(repo.findAll(specification, 10).map { UserResponse(it) }.toList())
        }
    }

    fun setPermissions(ctx: Context) {
        val userRepository = repositoryService.createUserRepository()
        userRepository.use { repo ->
            val user = repo.findById(ctx.pathParam("id", Int::class.java).get()) ?: throw NotFoundResponse()
            val permissions = JSONArray(ctx.body()).map { it as String }
            user.permissions = permissions
            repo.update(user)
        }
    }

    fun getPermissions(ctx: Context) {
        val userRepository = repositoryService.createUserRepository()
        val id = ctx.pathParam("id", Int::class.java).get()
        userRepository.use {
            ctx.json((it.findById(id) ?: throw NotFoundResponse()).permissions)
        }
    }

    fun getCurrentUser(ctx: Context) {
        val caller = getCallerUser(ctx) ?: throw UnauthorizedResponse()
        ctx.json(UserResponse(caller))
    }
}
