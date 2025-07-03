package com.joao.infrastructure.docs

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.openapi.OpenAPIConfig
import io.ktor.server.plugins.openapi.openAPI
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.routing.routing

fun Application.configureSwagger() {
    routing {
        openAPI(path = "swagger/documentation.json", swaggerFile = "openapi/documentation.json")
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.json")
    }
}