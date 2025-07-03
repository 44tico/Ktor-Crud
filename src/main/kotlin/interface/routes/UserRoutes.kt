package com.joao.`interface`.routes

import com.joao.application.usecases.CreateUserUseCase
import com.joao.application.usecases.DeleteUserByIdUseCase
import com.joao.application.usecases.GetAllUserUseCase
import com.joao.application.usecases.GetUserByIdUseCase
import com.joao.application.usecases.UpdateUserByIdUseCase
import com.joao.domain.models.User
import com.joao.infrastructure.persistence.DatabaseUserRepository
import com.joao.infrastructure.security.BCryptHasher
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import kotlinx.serialization.Serializable

fun Route.userRoutes() {
    route("/users") {
        post {
            val request = call.receive<CreateUserRequest>()

            val useCase = CreateUserUseCase(
                userRepository = DatabaseUserRepository(),
                passwordHasher = BCryptHasher()
            )

            val user = useCase.execute(
                name = request.name,
                rawEmail = request.email,
                rawPassword = request.password
            )

            call.respond(UserResponse.from(user))
        }

        get {
            val useCase = GetAllUserUseCase(
                userRepository = DatabaseUserRepository()
            )

            val users = useCase.execute()
            call.respond(users.map { UserResponse.from(it) })
        }

        get("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@get
            }

            val useCase = GetUserByIdUseCase(
                userRepository = DatabaseUserRepository()
            )

            val user = useCase.execute(id)

            if (user == null) {
                call.respond(HttpStatusCode.NotFound, "Usuário não encontrado")
            } else {
                call.respond(UserResponse.from(user))
            }
        }

        put("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@put
            }

            val request = call.receive<UpdateUserRequest>()
            val useCase = UpdateUserByIdUseCase(
                userRepository = DatabaseUserRepository()
            )

            val success = useCase.execute(id, request.name)
            if (success) {
                call.respond(HttpStatusCode.OK, "Usuário atualizado")
            } else {
                call.respond(HttpStatusCode.NotFound, "Usuário não encontrado")
            }
        }

        delete("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@delete
            }

            val useCase = DeleteUserByIdUseCase(
                userRepository = DatabaseUserRepository()
            )

            val sucess = useCase.execute(id)

            if (sucess) {
                call.respond(HttpStatusCode.OK, "Usuário deletado")
            } else {
                call.respond(HttpStatusCode.NotFound, "Usuário não encontrado")
            }
        }
    }
}

@Serializable
data class CreateUserRequest(val name: String, val email: String, val password: String)

@Serializable
data class UpdateUserRequest(val name: String)

@Serializable
data class UserResponse(val name: String, val email: String) {
    companion object {
        fun from(user: User) = UserResponse(
            name = user.name,
            email = user.email.getValue(),
        )
    }
}