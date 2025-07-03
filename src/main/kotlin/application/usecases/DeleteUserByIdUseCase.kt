package com.joao.application.usecases

import com.joao.domain.repositories.UserRepository

class DeleteUserByIdUseCase(private val userRepository: UserRepository) {
    suspend fun execute(id: Int): Boolean {
        return userRepository.delete(id)
    }
}