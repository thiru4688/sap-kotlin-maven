package sap.kotlin.maven.service

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service
import sap.kotlin.maven.dynamodb.UserEntity
import sap.kotlin.maven.dynamodb.UserRepository
import sap.kotlin.maven.model.UserDto

@Service
class UserService(
    private val userRepository: UserRepository
) {

    /* ---------------- READ ---------------- */

    @Cacheable("users")
    fun getAllUsers(): List<UserDto> {
        println("🔵 Fetching ALL users from DynamoDB")
        return userRepository.findAll()
            .map {
                UserDto(
                    it.id!!,
                    it.name!!,
                    it.email!!
                )
            }
    }

    @Cacheable(value = ["user"], key = "#id")
    fun getUserById(id: String): UserDto? {
        println("🔵 Fetching user [$id] from DynamoDB")
        return userRepository.findById(id)
            ?.let {
                UserDto(
                    it.id!!,
                    it.name!!,
                    it.email!!
                )
            }
    }

    /* ---------------- CREATE ---------------- */

    @CacheEvict(value = ["users"], allEntries = true)
    fun createUser(dto: UserDto): UserDto {
        println("🟢 Creating user [${dto.id}] → evicting users cache")

        val entity = UserEntity(
            id = dto.id,
            name = dto.name,
            email = dto.email
        )

        userRepository.save(entity)
        return dto
    }

    /* ---------------- UPDATE ---------------- */

    @CachePut(value = ["user"], key = "#dto.id")
    @CacheEvict(value = ["users"], allEntries = true)
    fun updateUser(dto: UserDto): UserDto {
        println("🟡 Updating user [${dto.id}] → updating cache + evicting users cache")

        val existing = userRepository.findById(dto.id)
            ?: throw IllegalArgumentException("User not found: ${dto.id}")

        val updated = existing.copy(
            name = dto.name,
            email = dto.email
        )

        userRepository.save(updated)
        return dto
    }

    /* ---------------- DELETE ---------------- */

    @Caching(
        evict = [
            CacheEvict(value = ["user"], key = "#id"),
            CacheEvict(value = ["users"], allEntries = true)
        ]
    )
    fun deleteUser(id: String): Boolean {
        println("🔴 Deleting user [$id] → evicting user + users cache")
        userRepository.deleteById(id)
        return true
    }
}
