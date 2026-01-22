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
}
