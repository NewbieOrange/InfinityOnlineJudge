package xyz.chengzi.ooad.service

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.apibuilder.EndpointGroup
import io.javalin.http.Context
import io.javalin.http.Handler
import xyz.chengzi.ooad.controller.*
import xyz.chengzi.ooad.server.ApplicationServer

class RestService(server: ApplicationServer, private val port: Int) {
    private val contestController = ContestController(server)
    private val problemController = ProblemController(server)
    private val submissionController = SubmissionController(server)
    private val userController = UserController(server)
    private val sessionController = SessionController(server)

    private val app = Javalin.create { config ->
        config.enableCorsForAllOrigins()
    }.routes {
        path("api") {
            path("contests") {
                get(contestController::listAll)
                post(contestController::create)
            }
            path("problems") {
                get(problemController::listAll)
                post(problemController::create)
                path(":id") {
                    get(problemController::getById)
                    patch(problemController::update)
                    delete(problemController::remove)
                    get("ranklist", problemController::listSubmissionRank)
                    path("files") {
                        get(problemController::listFiles)
                        path(":fileName") {
                            get(problemController::getFile)
                            post(problemController::createFile)
                            delete(problemController::deleteFile)
                        }
                    }
                }
            }
            path("submissions") {
                get(submissionController::listAll)
                post(submissionController::create)
                path(":id") {
                    get(submissionController::getById)
                    get("code", submissionController::getFile)
                }
            }
            path("users") {
                get(userController::listAll)
                post(userController::create)
                path(":id") {
                    get(userController::getById)
                    path("permissions") {
                        get(userController::getPermissions)
                        post(userController::setPermissions)
                    }
                }
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

    fun register(method: String, path: String, handler: (Context) -> Unit) {
        when (method.toLowerCase()) {
            "get" -> app.get(path, handler)
            "post" -> app.post(path, handler)
            "patch" -> app.patch(path, handler)
            "delete" -> app.delete(path, handler)
        }
    }

    fun start() {
        app.start(port)
    }

    fun close() {
        app.stop()
    }
}
