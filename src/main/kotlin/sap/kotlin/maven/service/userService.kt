/*
package sap.kotlin.maven.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import sap.kotlin.maven.model.UserDto
import sap.kotlin.maven.model.UserEntity
import sap.kotlin.maven.model.toDto
import sap.kotlin.maven.repo.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun getUsers(): List<UserDto> =
        userRepository.findAll().map {
            UserDto(
                id = it.id,
                name = it.name,
                email = it.email
            )
        }

    suspend fun createUser (name: String, email: String): UserDto {
       val entity = UserEntity(
            name = name,
            email = email
        )
        return userRepository.save(entity).toDto()
    }

    suspend fun updateUser(id: Long, name: String, email: String): UserEntity {
        val user = userRepository.findById(id)
            .orElseThrow { RuntimeException("User not found with id=$id") }
        user.name = name
        user.email = email
        return userRepository.save(user)
    }


    suspend fun deleteUser(id: Long): String {
        // existsById returns a Boolean directly
        val exists = withContext(Dispatchers.IO) {
            userRepository.existsById(id)
        }

        if (!exists) {
            throw RuntimeException("User not found with id=$id")
        }

        withContext(Dispatchers.IO) {
            userRepository.deleteById(id)
        }

        return "User with id $id deleted"
    }
}
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<Map<String, Any?>> {
        val errorMessage =  "User not found with id=${ex.message}"

        // Check if it's a "Not Found" case to return 404, otherwise 500
        val status = if (errorMessage.contains("not found", ignoreCase = true)) {
            HttpStatus.NOT_FOUND
        } else {
            HttpStatus.INTERNAL_SERVER_ERROR
        }

        val body = mapOf(
            "status" to status.value(),
            "message" to errorMessage,
            "timestamp" to java.time.LocalDateTime.now().toString()
        )

        return ResponseEntity.status(status).body(body)
    }
}*/
