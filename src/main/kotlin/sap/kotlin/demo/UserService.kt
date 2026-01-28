package sap.kotlin.demo

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val repo: UserRepository) {

    @CachePut(value = ["users"], key = "#result.id")
    fun save(name: String, email: String): User =
        repo.save(
            User().apply {
                println("Inside save of dynamodb")
                id = UUID.randomUUID().toString()
                this.name = name
                this.email = email
                println("Done save using dynamodb")
            }
        )

    @Cacheable(value = ["users"], key = "#id")
    fun findById(id: String): User? {
        println("Fetching from dynamodb")
        return repo.findById(id)
    }

    fun findAll() = repo.findAll()
    @CacheEvict(  value = ["users"],
        allEntries = true,
        beforeInvocation = true)
    fun deleteById(id: String) = repo.deleteById(id)
}
