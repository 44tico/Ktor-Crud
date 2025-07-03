package com.joao.application.usecases

import com.joao.domain.models.User
import com.joao.domain.repositories.UserRepository

class GetUserByIdUseCase(private val userRepository: UserRepository) {
    suspend fun execute(id: Int): User? {
        return userRepository.findById(id)
    }
}