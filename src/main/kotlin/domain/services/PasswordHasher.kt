package com.joao.domain.services

interface PasswordHasher {
    fun hash(password: String): String
}