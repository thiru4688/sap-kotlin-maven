package sap.kotlin.maven.service

import org.springframework.stereotype.Service
import sap.kotlin.maven.entity.UserEntity
import sap.kotlin.maven.model.UserDto
import sap.kotlin.maven.repository.UserRepository
import kotlin.collections.map


@Service
class UserService (
    private val userRepository: UserRepository
){
    fun getUsers(): List<UserDto> =
        userRepository.findAll().map {
            UserDto(it.id!!, it.name!!, it.email!!)
        }

    fun getById(id: String): UserDto? =
        userRepository.findById(id)?.toDto()

    fun createUser(id:String , name: String, email: String): UserDto {
        val entity = UserEntity().apply {
            this.id = id
            this.name = name
            this.email =email
        }
        return userRepository.save(entity).toDto()
    }

    fun updateUser(id:String , name: String, email: String): UserDto {
        val existing = userRepository.findById(id)
            ?: throw RuntimeException("User not found")

        existing.name = name
        existing.email = email

        return userRepository.save(existing).toDto()
    }

    fun deleteUser(id: String): Boolean =
        userRepository.delete(id)



    private fun UserEntity.toDto() =
        UserDto(id!!, name!!, email!!)
}