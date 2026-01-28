// kotlin
package sap.kotlin.maven.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import sap.kotlin.maven.model.UserDto
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException
import software.amazon.awssdk.services.dynamodb.model.ScanRequest
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest
import java.time.Duration

@Repository
class UserRepository(
    private val dynamoDbClient: DynamoDbClient,
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
    @Value("\${aws.dynamodb.table.users:Users}")
    private val tableName: String
) {

    private val log = LoggerFactory.getLogger(UserRepository::class.java)
    private val userKeyPrefix = "user:"
    private val allUsersKey = "users:all"
    private val cacheTtl = Duration.ofMinutes(10)

    fun findAll(): List<UserDto> {
        try {
            // Try cache
            redisTemplate.opsForValue().get(allUsersKey)?.let { cached ->
                return objectMapper.readValue<List<UserDto>>(cached)
            }

            val scanRequest = ScanRequest.builder()
                .tableName(tableName)
                .build()

            val result = dynamoDbClient.scan(scanRequest)

            val users = result.items().map { mapToUserDto(it) }

            val json = objectMapper.writeValueAsString(users)
            redisTemplate.opsForValue().set(allUsersKey, json, cacheTtl)

            return users
        } catch (e: DynamoDbException) {
            // Log and return empty list on DynamoDB errors
            log.error("Error scanning DynamoDB table '{}'", tableName, e)
            return emptyList()
        } catch (e: Exception) {
            log.error("Error in findAll cache/serialization", e)
            return emptyList()
        }
    }

    fun findById(id: Int): UserDto? {
        val cacheKey = "$userKeyPrefix$id"
        try {
            // Try cache
            redisTemplate.opsForValue().get(cacheKey)?.let { cached ->
                return objectMapper.readValue<UserDto>(cached)
            }

            val key = mapOf("id" to AttributeValue.builder().n(id.toString()).build())
            val request = GetItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .build()

            val response = dynamoDbClient.getItem(request)
            val item = response.item()
            val user = if (item == null || item.isEmpty()) null else mapToUserDto(item)
            if (user != null) {
                val json = objectMapper.writeValueAsString(user)
                redisTemplate.opsForValue().set(cacheKey, json, cacheTtl)
            }
            return user
        } catch (e: DynamoDbException) {
            log.error("Error getting item from table '{}' for id={}", tableName, id, e)
            return null
        } catch (e: Exception) {
            log.error("Error in findById cache/serialization for id={}", id, e)
            return null
        }
    }

    fun save(user: UserDto): Boolean {
        try {
            val item = mapOf(
                "id" to AttributeValue.builder().n(user.id.toString()).build(),
                "name" to AttributeValue.builder().s(user.name).build(),
                "email" to AttributeValue.builder().s(user.email).build()
            )

            val request = PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build()

            dynamoDbClient.putItem(request)

            // update cache for this user and invalidate all-users cache
            val cacheKey = "$userKeyPrefix${user.id}"
            val json = objectMapper.writeValueAsString(user)
            redisTemplate.opsForValue().set(cacheKey, json, cacheTtl)
            redisTemplate.delete(allUsersKey)

            return true
        } catch (e: DynamoDbException) {
            log.error("Error putting item into table '{}' for id={}", tableName, user.id, e)
            return false
        } catch (e: Exception) {
            log.error("Error in save cache/serialization for id={}", user.id, e)
            return false
        }
    }

    fun deleteById(id: Int): Boolean {
        try {
            val key = mapOf("id" to AttributeValue.builder().n(id.toString()).build())
            val request = DeleteItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .build()

            dynamoDbClient.deleteItem(request)

            // evict caches
            val cacheKey = "$userKeyPrefix$id"
            redisTemplate.delete(cacheKey)
            redisTemplate.delete(allUsersKey)

            return true
        } catch (e: DynamoDbException) {
            log.error("Error deleting item from table '{}' for id={}", tableName, id, e)
            return false
        } catch (e: Exception) {
            log.error("Error evicting cache for id={}", id, e)
            return false
        }
    }

    private fun mapToUserDto(item: Map<String, AttributeValue>): UserDto {
        val id = item["id"]?.n()?.toIntOrNull() ?: 0
        val name = item["name"]?.s() ?: ""
        val email = item["email"]?.s() ?: ""

        return UserDto(id, name, email)
    }
}
