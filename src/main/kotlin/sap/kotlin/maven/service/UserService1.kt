package sap.kotlin.maven.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import sap.kotlin.maven.dbconfig.CacheService
import sap.kotlin.maven.model.User1
import sap.kotlin.maven.model.UserDto
import sap.kotlin.maven.model.toDto
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema

@Service
class UserService1(
    enhancedClient: DynamoDbEnhancedClient,
    cacheService: CacheService
) {

    private val userTable =
        enhancedClient.table("users", TableSchema.fromBean(User1::class.java))

    suspend fun getUsers(): List<UserDto> = withContext(Dispatchers.IO) {
        userTable.scan().items().map { it.toDto() }.toList()
    }

    suspend fun createUser(name: String, email: String): UserDto =
        withContext(Dispatchers.IO) {

            val entity = User1(
                id = java.util.Random().nextLong(1_000_000),
                name = name,
                email = email
            )

            userTable.putItem(entity)

            entity.toDto()
        }

    // Cacheable MUST be non-suspend (Spring limitation)
    @Cacheable(value = ["users"], key = "#id") // Redis cache by userId
    fun getUserById(id: Long): UserDto {
        val entity = userTable.getItem(
            Key.builder().partitionValue(id).build()
        ) ?: throw RuntimeException("User not found with id=$id")

        return entity.toDto()
    }

    @CacheEvict(value = ["users"], key = "#id")
    suspend fun updateUser(
        id: Long,
        name: String,
        email: String
    ): UserDto = withContext(Dispatchers.IO) {

        val existing = userTable.getItem(
            Key.builder().partitionValue(id).build()
        ) ?: throw RuntimeException("User not found with id=$id")

        existing.name = name
        existing.email = email

        userTable.putItem(existing)

        existing.toDto()
    }

    @CacheEvict(value = ["users"], key = "#id")
    suspend fun deleteUser(id: Long): String = withContext(Dispatchers.IO) {

        val key = Key.builder().partitionValue(id).build()

        userTable.deleteItem(key)
            ?: throw RuntimeException("User not found with id=$id")

        "User with id $id deleted"
    }
}
