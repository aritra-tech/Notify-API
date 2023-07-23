package com.aritra.plugins

import com.aritra.routes.noteRoutes
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        noteRoutes()
        get("/") {
            //This is a comment
            call.respondText("Hello World!")
        }
    }
}
