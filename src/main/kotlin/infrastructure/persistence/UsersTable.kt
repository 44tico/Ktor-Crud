package com.joao.infrastructure.persistence

import org.jetbrains.exposed.sql.Table

object UsersTable : Table("users") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val email = varchar("email", 255).uniqueIndex()
    val hashedPassword = varchar("hashedPassword", 255)

    override val primaryKey = PrimaryKey(id)
}