package com.joao.application.usecases

import com.joao.domain.models.User
import com.joao.domain.repositories.UserRepository

class UpdateUserByIdUseCase(private val userRepository: UserRepository) {
    suspend fun execute(id: Int, name: String): Boolean {
        return userRepository.update(id, name)
    }
}