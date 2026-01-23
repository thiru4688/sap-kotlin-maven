package sap.kotlin.maven.service

import org.springframework.stereotype.Service
import sap.kotlin.maven.dynamodb.UserEntity
import sap.kotlin.maven.dynamodb.UserRepository
import sap.kotlin.maven.model.UserDto

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<UserDto> {
        return userRepository.findAll().map {
            UserDto(it.id!!, it.name!!, it.email!!)
        }
    }

    fun getUserById(id: String): UserDto? {
        return userRepository.findById(id)?.let {
            UserDto(it.id!!, it.name!!, it.email!!)
        }
    }

    fun createUser(dto: UserDto): UserDto {
        val entity = UserEntity(dto.id, dto.name, dto.email)
        userRepository.save(entity)
        return dto
    }

    fun updateUser(dto: UserDto): UserDto {
        val existing = userRepository.findById(dto.id)
            ?: throw RuntimeException("User not found")

        val updated = UserEntity(
            id = dto.id,
            name = dto.name,
            email = dto.email
        )

        userRepository.save(updated)
        return dto
    }


    fun deleteUser(id: String): Boolean {
        val existing = userRepository.findById(id) ?: return false
        userRepository.deleteById(id)
        return true
    }
}
