package sap.kotlin.maven.service

import org.springframework.stereotype.Service
import sap.kotlin.maven.model.UserDto
import sap.kotlin.maven.model.UserEntity
import sap.kotlin.maven.model.toDto
import sap.kotlin.maven.repo.UserRepository
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun getUsers(): List<UserDto> =
        userRepository.findAll().map {
            UserDto(
                id = it.id,
                name = it.name,
                email = it.email
            )
        }

    suspend fun createUser(id:String,name: String, email: String): UserDto {
        val entity = UserEntity(
            id = id,
            name = name,
            email = email
        )

        return userRepository.save(entity).toDto()
    }

}
