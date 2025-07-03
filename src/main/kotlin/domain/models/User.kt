package com.joao.domain.models

data class User(
    val id: Int,
    val name: String,
    val email: Email,
    val hashedPassword: String
) {
}