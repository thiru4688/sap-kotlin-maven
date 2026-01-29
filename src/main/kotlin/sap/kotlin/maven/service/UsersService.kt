package sap.kotlin.maven.service


import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import sap.kotlin.maven.model.Users
import sap.kotlin.maven.repository.UsersRepository
import org.springframework.stereotype.Service

@Service
class UsersService(private val userRepository: UsersRepository) {

    fun createUser(user: Users) = userRepository.save(user)

    @Cacheable(value = ["users"], key = "#id")
    fun getUser(id: Long) = userRepository.findById(id)

    fun getAllUsers() = userRepository.findAll()

    @CacheEvict(value = ["users"], key = "#id")
    fun updateUser(user: Users) = userRepository.update(user)

    @CacheEvict(value = ["users"], key = "#id")
     fun deleteUser(id: Long) = userRepository.delete(id)
}