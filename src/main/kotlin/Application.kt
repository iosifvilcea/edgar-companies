package com.blankthings

import CompanyRepositoryImpl
import database.configureDatabases
import io.ktor.server.application.*
import io.ktor.server.plugins.ratelimit.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlin.time.Duration.Companion.seconds

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val companyRepository = CompanyRepositoryImpl()

    configureDatabases()
    configureStatusPages()
    configureRateLimit()
    configureRouting(companyRepository)
}

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<IllegalStateException> { call, cause ->
            call.respondText("App in illegal state as ${cause.message}")
        }
    }
}

fun Application.configureRateLimit() {
    install(RateLimit) {
        global {
            rateLimiter(limit = 10, refillPeriod = 1.seconds)
        }
    }
}
