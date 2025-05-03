package com.teya.routes

import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.helloRoutes() {
    route("/hello") {
        get {
            call.respond("Hello World!")
        }
    }
}