package com.joao.application.usecases

import com.joao.domain.models.User
import com.joao.domain.repositories.UserRepository

class GetAllUserUseCase(private val userRepository: UserRepository) {
    suspend fun execute(): List<User> {
        return userRepository.findAll()
    }
}