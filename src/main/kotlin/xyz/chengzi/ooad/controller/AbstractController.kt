package xyz.chengzi.ooad.controller

import io.javalin.http.Context
import xyz.chengzi.ooad.entity.User
import xyz.chengzi.ooad.repository.Repository
import xyz.chengzi.ooad.server.ApplicationServer

abstract class AbstractController(protected val server: ApplicationServer) {
    protected val repositoryService = server.repositoryService!!
    protected val sessionService = server.sessionService!!
    protected val propertiesService = server.propertiesService!!

    /**
     * Get the caller user of the ctx, null if the caller is not authorized.
     *
     * @param ctx the context.
     * @return the caller user, null if the caller is not authorized.
     */
    fun getCallerUser(userRepository: Repository<User>, ctx: Context): User? {
        val token = ctx.cookie("token")
        return if (token == null) null else sessionService.findTokenOwner(userRepository, token.toByteArray())
    }

    fun checkPermission(user: User, permission: String): Boolean {
        return user.hasPermission(permission) || user.hasPermission("admin")
    }
}
