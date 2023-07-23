package com.aritra.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        get("/") {
            //This is a comment
            call.respondText("Hello World!")
        }
    }
}
