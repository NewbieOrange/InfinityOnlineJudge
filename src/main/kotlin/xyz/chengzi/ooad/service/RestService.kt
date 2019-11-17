package xyz.chengzi.ooad.service

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import xyz.chengzi.ooad.controller.SessionController
import xyz.chengzi.ooad.controller.UserController
import xyz.chengzi.ooad.server.ApplicationServer

class RestService(server: ApplicationServer, private val port: Int) {
    private val userController = UserController(server)
    private val sessionController = SessionController(server)
    private val app = Javalin.create().routes {
        path("api") {
            path("users") {
                post(userController::createUser)
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
