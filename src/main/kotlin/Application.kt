package com.blankthings

import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
}
