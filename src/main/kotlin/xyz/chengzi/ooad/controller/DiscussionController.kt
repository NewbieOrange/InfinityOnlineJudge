package xyz.chengzi.ooad.controller

import io.javalin.http.Context
import xyz.chengzi.ooad.dto.CreateDiscussionRequest
import xyz.chengzi.ooad.dto.DiscussionCommentResponse
import xyz.chengzi.ooad.dto.DiscussionThreadBriefResponse
import xyz.chengzi.ooad.dto.DiscussionThreadResponse
import xyz.chengzi.ooad.entity.DiscussionComment
import xyz.chengzi.ooad.entity.DiscussionThread
import xyz.chengzi.ooad.repository.discussionthread.DiscussionThreadTitleNotNullSpecification
import xyz.chengzi.ooad.repository.entity.SinceIdSpecification
import xyz.chengzi.ooad.server.ApplicationServer
import java.util.*

class DiscussionController(server: ApplicationServer) : AbstractController(server) {
    fun createThread(ctx: Context) {
        val userRepository = repositoryService.createUserRepository()
        val threadRepository = repositoryService.createDiscussionThreadRepository()

        val request = ctx.bodyAsClass(CreateDiscussionRequest::class.java)
        threadRepository.use { repo ->
            val discussion = DiscussionThread()
            discussion.title = request.title
            discussion.user = userRepository.use {
                getCallerUser(it, ctx)
            }
            discussion.content = request.content
            discussion.timestamp = Date()
            repo.add(discussion)
        }
    }


    fun createComment(ctx: Context) {
        val userRepository = repositoryService.createUserRepository()
        val commentRepository = repositoryService.createDiscussionCommentRepository()
        val threadRepository = repositoryService.createDiscussionThreadRepository()

        val request = ctx.bodyAsClass(CreateDiscussionRequest::class.java)
        commentRepository.use { repo ->
            val parent = request.parent?.let { repo.findById(it) }
            val discussion = DiscussionComment()
            discussion.parent = parent
            discussion.user = userRepository.use {
                getCallerUser(it, ctx)
            }
            discussion.content = request.content
            discussion.timestamp = Date()
            repo.add(discussion)

            if (discussion.parent == null) {
                discussion.thread = threadRepository.use {
                    it.findById(ctx.pathParam("id").toInt())
                }
                repo.update(discussion)
            }
        }
    }

    fun createInProblem(ctx: Context) {
        val userRepository = repositoryService.createUserRepository()
        val problemRepository = repositoryService.createProblemRepository()
        val threadRepository = repositoryService.createDiscussionThreadRepository()
        val commentRepository = repositoryService.createDiscussionCommentRepository()

        val request = ctx.bodyAsClass(CreateDiscussionRequest::class.java)
        commentRepository.use { repo ->
            val thread = problemRepository.use {
                val problem = it.findById(ctx.pathParam("id").toInt())!!
                if (problem.discussionThread == null) {
                    problem.discussionThread = DiscussionThread()
                    problem.discussionThread.timestamp = Date()
                    threadRepository.add(problem.discussionThread)
                    it.update(problem)
                }
                problem.discussionThread.id
            }

            val discussion = DiscussionComment()
            discussion.parent = request.parent?.let { repo.findById(it) }
            discussion.timestamp = Date()
            discussion.user = userRepository.use {
                getCallerUser(it, ctx)
            }
            discussion.content = request.content
            repo.add(discussion)

            if (discussion.parent == null) {
                threadRepository.use {
                    discussion.thread = it.findById(thread)
                    repo.update(discussion)
                }
            }
        }
    }

    fun listAllInThread(ctx: Context) {
        val threadRepository = repositoryService.createDiscussionThreadRepository()
        threadRepository.use {
            val discussionThread = it.findById(ctx.pathParam("id").toInt())!!
            ctx.json(DiscussionThreadResponse(discussionThread))
        }
    }

    fun listAllInProblem(ctx: Context) {
        val problemRepository = repositoryService.createProblemRepository()
        problemRepository.use {
            val problem = it.findById(ctx.pathParam("id").toInt())!!
            ctx.json(DiscussionThreadResponse(problem.discussionThread))
        }
    }

    fun listThreads(ctx: Context) {
        val threadRepository = repositoryService.createDiscussionThreadRepository()
        val since = ctx.queryParam("since", "0")!!.toInt()
        threadRepository.use { repo ->
            val items = repo.findAll(DiscussionThreadTitleNotNullSpecification().and(SinceIdSpecification(since)), 10)
            ctx.json(items.map { DiscussionThreadBriefResponse(it) }.toList())
        }
    }
}
