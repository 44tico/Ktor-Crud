package com.joao

import com.joao.infrastructure.config.configureDatabase
import com.joao.infrastructure.config.configureSerialization
import com.joao.infrastructure.docs.configureSwagger
import com.joao.`interface`.configureRouting
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureDatabase()
    configureSwagger()
    configureSerialization()
    configureRouting()
}
