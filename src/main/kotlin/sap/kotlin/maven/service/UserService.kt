package sap.kotlin.maven.service

import org.springframework.stereotype.Service
import sap.kotlin.maven.model.UserDto
import sap.kotlin.maven.repository.UserRepository

@Service
class UserService(private val userRepository: UserRepository) {

    fun fetchUsers(): List<UserDto> {
        return userRepository.findAll()
    }

    fun findAll(): List<UserDto> {
        return userRepository.findAll()
    }

    fun findById(id: Int): UserDto? {
        return userRepository.findById(id)
    }

    fun save(user: UserDto): Boolean {
        return userRepository.save(user)
    }

    fun deleteById(id: Int): Boolean {
        return userRepository.deleteById(id)
    }
}
