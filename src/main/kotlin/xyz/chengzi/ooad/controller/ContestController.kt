package xyz.chengzi.ooad.controller

import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import xyz.chengzi.ooad.dto.CreateContestRequest
import xyz.chengzi.ooad.entity.Contest
import xyz.chengzi.ooad.repository.entity.SinceIdSpecification
import xyz.chengzi.ooad.server.ApplicationServer

class ContestController(server: ApplicationServer) : AbstractController(server) {
    fun create(ctx: Context) {
        val contestRepository = repositoryService.createContestRepository()
        val request = ctx.bodyAsClass(CreateContestRequest::class.java)
        contestRepository.use {
            val item = Contest()
            item.title = request.title
            item.description = request.description
            item.startDate = request.startDate
            item.endDate = request.endDate
            item.visibility = request.visibility
            item.mode = request.mode
            it.add(item)
        }
    }

    fun listAll(ctx: Context) {
        val contestRepository = repositoryService.createContestRepository()
        val since = ctx.queryParam("since", "0")!!.toInt()
        contestRepository.use {
            val contests = it.findAll(SinceIdSpecification(since), 10)
            ctx.json(contests)
        }
    }

    private val routeModeHandlerMap = HashMap<String, HashMap<String, (Context) -> Unit>>()

    fun registerMode(method: String, mode: String, path: String, handler: (Context) -> Unit) {
        val route = "$method%$path"
        if (routeModeHandlerMap[route] == null) {
            routeModeHandlerMap[route] = HashMap()
            server.restService.register(method, "/api/contests/:id/$path") { ctx ->
                val contestRepository = repositoryService.createContestRepository()
                val contest = contestRepository.use {
                    it.findById(ctx.pathParam("id").toInt())
                }!!
                val modeHandler = routeModeHandlerMap[route]!![contest.mode] ?: throw NotFoundResponse()
                modeHandler(ctx)
            }
        }
        routeModeHandlerMap[route]!![mode] = handler
    }
}
