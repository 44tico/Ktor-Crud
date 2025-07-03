package com.joao.domain.repositories

import com.joao.domain.models.User

interface UserRepository {
    suspend fun save(user: User): User
    suspend fun findById(id: Int): User?
    suspend fun findAll(): List<User>
    suspend fun update(id: Int, name: String): Boolean
    suspend fun delete(id: Int): Boolean
}