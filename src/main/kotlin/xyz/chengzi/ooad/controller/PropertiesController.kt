package xyz.chengzi.ooad.controller

import io.javalin.http.Context
import xyz.chengzi.ooad.server.ApplicationServer

class PropertiesController(server: ApplicationServer) : AbstractController(server) {
    fun get(ctx: Context) {
        ctx.result(propertiesService.getProperty(ctx.pathParam("key")).orEmpty())
    }

    fun set(ctx: Context) {
        propertiesService.setProperty(ctx.pathParam("key"), ctx.body())
    }

    fun remove(ctx: Context) {
        propertiesService.removeProperty(ctx.pathParam("key"))
    }
}
