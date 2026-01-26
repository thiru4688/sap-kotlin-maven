/*
package sap.kotlin.maven.service

//import org.springframework.cache.annotation.CacheEvict
//import org.springframework.cache.annotation.CachePut
//import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import sap.kotlin.maven.model.UsersDto
import sap.kotlin.maven.repository.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository
) {
//    @Cacheable(value = ["users"])
    fun getAllUsers(): List<UsersDto> = userRepository.findAll()

//    @Cacheable(value = ["user"], key = "#id")
    fun getUserById(id: Int): UsersDto? = userRepository.findById(id).orElse(null)

//    @CachePut(value = ["user"], key = "#user.id")
    fun createUser(user: UsersDto): UsersDto = userRepository.save(user)

//    @CachePut(value = ["user"], key = "#id")
    fun updateUser(id: Int, updated: UsersDto): UsersDto? {
        return userRepository.findById(id).map {
            val newUser = it.copy(name = updated.name, email = updated.email)
            userRepository.save(newUser)
        }.orElse(null)
    }

//    @CacheEvict(value = ["user"], key = "#id")
    fun deleteUser(id: Int): Boolean {
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            true
        } else {
            false
        }
    }

}*/
