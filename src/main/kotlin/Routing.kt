package com.blankthings

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(companyRepository: CompanyRepository) {
    routing {
        get("/") {
            call.respondText("Hello World.")
        }
        companiesRoutes(companyRepository)
    }
}
