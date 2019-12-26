package xyz.chengzi.ooad.service

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.http.Context
import xyz.chengzi.ooad.controller.*
import xyz.chengzi.ooad.server.ApplicationServer

class RestService(server: ApplicationServer, private val port: Int) {
    private val dynamicRoutes = HashMap<String, (Context) -> Unit>()

    val contestController = ContestController(server)
    val problemController = ProblemController(server)
    val discussionController = DiscussionController(server)
    val submissionController = SubmissionController(server)
    val userController = UserController(server)
    val sessionController = SessionController(server)
    val propertiesController = PropertiesController(server)

    private val app = Javalin.create { config ->
        config.enableCorsForAllOrigins()
    }.routes {
        path("api") {
            path("contests") {
                get(contestController::listAll)
                post(contestController::create)
            }
            path("discussions") {
                get(discussionController::listThreads)
                post(discussionController::createThread)
                path(":id") {
                    get(discussionController::listAllInThread)
                    post(discussionController::createComment)
                }
            }
            path("problems") {
                get(problemController::listAll)
                post(problemController::create)
                path(":id") {
                    get(problemController::getById)
                    patch(problemController::update)
                    delete(problemController::remove)
                    get("ranklist", problemController::listSubmissionRank)
                    path("discussions") {
                        get(discussionController::listAllInProblem)
                        post(discussionController::createInProblem)
                    }
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
                patch(userController::update)
            }
            path("session") {
                post(sessionController::login)
                delete(sessionController::logout)
            }
            path("properties") {
                path(":key") {
                    get(propertiesController::get)
                    post(propertiesController::set)
                    delete(propertiesController::remove)
                }
            }
        }
    }

    fun register(method: String, path: String, handler: (Context) -> Unit) {
        val method = method.toLowerCase()
        if (dynamicRoutes["$method@$path"] == null) {
            val dynamicHandler = fun(ctx: Context) {
                dynamicRoutes["$method@$path"]?.let { it(ctx) }
            }
            when (method) {
                "get" -> app.get(path, dynamicHandler)
                "post" -> app.post(path, dynamicHandler)
                "put" -> app.put(path, dynamicHandler)
                "patch" -> app.patch(path, dynamicHandler)
                "delete" -> app.delete(path, dynamicHandler)
            }
        }
        dynamicRoutes["$method@$path"] = handler
    }

    fun start() {
        app.start(port)
    }

    fun close() {
        app.stop()
    }
}
