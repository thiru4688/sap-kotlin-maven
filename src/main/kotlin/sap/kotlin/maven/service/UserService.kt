package sap.kotlin.maven.service

import sap.kotlin.maven.repository.UserDynamoRepository
import org.springframework.stereotype.Service
import sap.kotlin.maven.entity.UserDynamoEntity

@Service
class UserService(private val userDynamoRepository: UserDynamoRepository) {

    fun getAllUsers(): List<UserDynamoEntity> {
        return userDynamoRepository.findAll()
    }

    fun getUserById(id: String): UserDynamoEntity? {
        return userDynamoRepository.findById(id)
    }

    fun createUser(user: UserDynamoEntity): UserDynamoEntity {
        return userDynamoRepository.save(user)
    }

    fun updateUser(user: UserDynamoEntity): UserDynamoEntity {
        return userDynamoRepository.save(user)
    }

    fun deleteUser(id: String): Boolean {
        return userDynamoRepository.deleteById(id)
    }
}



/*
                        In-memory repository usage

package sap.kotlin.maven.service

import org.springframework.stereotype.Service
import sap.kotlin.maven.exception.UserNotFoundException
import sap.kotlin.maven.model.UserDto
import sap.kotlin.maven.repository.userDynamoRepository

@Service
class UserService(private val userDynamoRepository: userDynamoRepository) {

    fun getAllUsers(): List<UserDto> {
        val users = userDynamoRepository.findAll()

        if (users.isEmpty()) {
            throw UserNotFoundException("No users to display.")
        }

        return users
    }

    fun getUserById(id: String): UserDto {
        return userDynamoRepository.findById(id)
            ?: throw UserNotFoundException("User with given id not exists.")
    }

    fun createUser(user: UserDto): UserDto {
        return userDynamoRepository.save(user)
    }

    fun updateUser(user: UserDto): UserDto {

        val existing = userDynamoRepository.findById(user.id)

        if (existing == null) {
            throw UserNotFoundException("The user you are trying to update is not available.")
        }

        return userDynamoRepository.save(user)
    }

    fun deleteUser(id: String) {

        val existing = userDynamoRepository.findById(id)

        if (existing == null) {
            throw UserNotFoundException("User with given id is not available")
        }

        userDynamoRepository.delete(id)
    }
}


 */