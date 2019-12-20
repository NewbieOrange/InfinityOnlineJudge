package xyz.chengzi.ooad.controller

import io.javalin.http.Context
import xyz.chengzi.ooad.dto.CreateDiscussionRequest
import xyz.chengzi.ooad.dto.DiscussionResponse
import xyz.chengzi.ooad.entity.Discussion
import xyz.chengzi.ooad.server.ApplicationServer

class DiscussionController(server: ApplicationServer) : AbstractController(server) {
    fun create(ctx: Context) {
        val userRepository = repositoryService.createUserRepository()
        val problemRepository = repositoryService.createProblemRepository()
        val discussionRepository = repositoryService.createDiscussionRepository()

        val request = ctx.bodyAsClass(CreateDiscussionRequest::class.java)
        discussionRepository.use { repo ->
            val parent = repo.findById(request.parent ?: problemRepository.use {
                val item = it.findById(ctx.pathParam("id").toInt())!!
                if (item.discussion == null) {
                    item.discussion = Discussion()
                    repo.add(item.discussion)
                    it.update(item)
                }
                item.discussion.id
            })
            val discussion = Discussion()
            discussion.parent = parent
            discussion.user = userRepository.use {
                getCallerUser(it, ctx)
            }
            discussion.comment = request.comment
            repo.add(discussion)
        }
    }

    fun listAll(ctx: Context) {
        val problemRepository = repositoryService.createProblemRepository()
        problemRepository.use {
            val problem = it.findById(ctx.pathParam("id").toInt())!!
            ctx.json(DiscussionResponse(problem.discussion))
        }
    }
}
