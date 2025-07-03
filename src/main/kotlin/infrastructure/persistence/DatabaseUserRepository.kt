package com.joao.infrastructure.persistence

import com.joao.domain.models.Email
import com.joao.domain.models.User
import com.joao.domain.repositories.UserRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseUserRepository : UserRepository {
    override suspend fun save(user: User): User {
        return transaction {
            val insertStatement = UsersTable.insert {
                it[name] = user.name
                it[email] = user.email.getValue()
                it[hashedPassword] = user.hashedPassword
            }
            user
        }
    }

    override suspend fun findById(id: Int): User? {
        return transaction {
            UsersTable
                .selectAll()
                .where { UsersTable.id eq id }
                .map { row ->
                    User(
                        id = row[UsersTable.id],
                        name = row[UsersTable.name],
                        email = Email(row[UsersTable.email]),
                        hashedPassword = row[UsersTable.hashedPassword]
                    )
                }
                .singleOrNull()
        }
    }

    override suspend fun findAll(): List<User> {
        return transaction {
            UsersTable.selectAll().map {
                User(
                    id = it[UsersTable.id],
                    name = it[UsersTable.name],
                    email = Email(it[UsersTable.email]),
                    hashedPassword = it[UsersTable.hashedPassword]
                )
            }
        }
    }

    override suspend fun update(id: Int, name: String): Boolean {
        return transaction {
            UsersTable.update( { UsersTable.id eq id } ) {
                it[UsersTable.name] = name
            } > 0
        }
    }

    override suspend fun delete(id: Int): Boolean {
        return transaction {
            UsersTable.deleteWhere { UsersTable.id eq id } > 0
        }
    }
}