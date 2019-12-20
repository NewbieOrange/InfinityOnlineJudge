package xyz.chengzi.ooad.controller

import io.javalin.http.Context
import xyz.chengzi.ooad.entity.Contest
import xyz.chengzi.ooad.entity.Problem
import xyz.chengzi.ooad.entity.Submission
import xyz.chengzi.ooad.entity.User
import xyz.chengzi.ooad.repository.JpaRepository
import xyz.chengzi.ooad.repository.Repository
import xyz.chengzi.ooad.repository.user.UserRepository
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
    fun getCallerUser(userRepository: Repository<User>, ctx: Context): User? {
        val token = ctx.cookie("token")
        return if (token == null) null else sessionService.findTokenOwner(userRepository, token.toByteArray())
    }

    fun checkPermission(user: User, permission: String): Boolean {
        return user.hasPermission(permission) || user.hasPermission("admin")
    }
}
