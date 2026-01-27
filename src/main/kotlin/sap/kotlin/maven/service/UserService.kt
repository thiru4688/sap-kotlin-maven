package sap.kotlin.maven.service

import org.springframework.stereotype.Service
import sap.kotlin.maven.model.UserDto
import sap.kotlin.maven.model.toDto
import sap.kotlin.maven.model.toEntity
//import sap.kotlin.maven.repository.UserCacheRepository
import sap.kotlin.maven.repository.UserDynamoRepository

@Service
class UserService(
    private val repo: UserDynamoRepository,
    //private val cache: UserCacheRepository
) {

    fun getAll(): List<UserDto> {

        // 1️⃣ Try Redis first
        /*cache.getAllUsers()?.let {
            println("Cache HIT: all users")
            return it
        }*/

        // 2️⃣ Cache miss → DynamoDB
        println("Cache MISS: all users")
        val users = repo.findAll().map { it.toDto() }

        // 3️⃣ Store in Redis
        //cache.saveAllUsers(users)

        return users
    }

    fun getById(id: String): UserDto {

        /*cache.getUserById(id)?.let {
            println("Cache HIT: user $id")
            return it
        }*/

        println("Cache MISS: user $id")
        val user = repo.findById(id)?.toDto()
            ?: throw RuntimeException("User not found")

        //cache.saveUser(user)
        return user
    }

    fun create(user: UserDto): UserDto {
        val saved = repo.save(user.toEntity()).toDto()

        // Invalidate cache
        /*cache.evictAll()
        cache.evictUser(saved.id)*/

        return saved
    }


    fun update(id: String, user: UserDto): UserDto {

        val existing = repo.findById(id)
            ?: throw RuntimeException("User not found")

        val updated = existing.copy(
            name = user.name,
            email = user.email
        )

        repo.save(updated)

        // Invalidate cache
        /*cache.evictAll()
        cache.evictUser(id)*/

        return updated.toDto()
    }


    fun delete(id: String) {
        repo.deleteById(id)

        // Invalidate cache
        /*cache.evictAll()
        cache.evictUser(id)*/
    }

}