package xyz.chengzi.ooad.service

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import xyz.chengzi.ooad.controller.*
import xyz.chengzi.ooad.server.ApplicationServer

class RestService(server: ApplicationServer, private val port: Int) {
    private val contestController = ContestController(server)
    private val problemController = ProblemController(server)
    private val submissionController = SubmissionController(server)
    private val userController = UserController(server)
    private val sessionController = SessionController(server)

    private val app = Javalin.create().routes {
        path("api") {
            path("contests") {
                get(contestController::listAll)
                post(contestController::create)
            }
            path("problems") {
                get(problemController::listAll)
                post(problemController::create)
            }
            path("submissions") {
                get(submissionController::listAll)
                post(submissionController::create)
            }
            path("users") {
                get(userController::listAll)
                post(userController::create)
            }
            path("user") {
                get(userController::getCurrentUser)
            }
            path("session") {
                post(sessionController::login)
                delete(sessionController::logout)
            }
        }
    }

    fun start() {
        app.start(port)
    }

    fun close() {
        app.stop()
    }
}
