package com.joao.infrastructure.config

import com.joao.infrastructure.persistence.UsersTable
import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.config.HoconApplicationConfig
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {
    val config = HoconApplicationConfig(ConfigFactory.load())
    val dbUrl = config.property("postgres.url").getString()
    val dbUser = config.property("postgres.user").getString()
    val dbPassword = config.property("postgres.password").getString()

    Database.connect(
        url = dbUrl,
        driver = "org.postgresql.Driver",
        user = dbUser,
        password = dbPassword
    )

    transaction {
        SchemaUtils.create(UsersTable)
    }
}