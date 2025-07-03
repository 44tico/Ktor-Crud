package com.joao.application.usecases

import com.joao.domain.models.Email
import com.joao.domain.models.User
import com.joao.domain.repositories.UserRepository
import com.joao.domain.services.PasswordHasher

class CreateUserUseCase(private val userRepository: UserRepository, private val passwordHasher: PasswordHasher) {
    suspend fun execute(name: String, rawEmail : String, rawPassword: String): User {
        val email = Email(rawEmail)
        val hashedPassword = passwordHasher.hash(rawPassword)
        val user = User(id = 0, name, email, hashedPassword)
        userRepository.save(user)
        return user
    }
}