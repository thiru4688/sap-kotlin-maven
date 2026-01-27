// kotlin
package sap.kotlin.maven.repository

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import sap.kotlin.maven.model.UserDto
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException
import software.amazon.awssdk.services.dynamodb.model.ScanRequest
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest

@Repository
class UserRepository(
    private val dynamoDbClient: DynamoDbClient,
    @Value("\${aws.dynamodb.table.users:Users}")
    private val tableName: String
) {

    private val log = LoggerFactory.getLogger(UserRepository::class.java)

    fun findAll(): List<UserDto> {
        try {
            val scanRequest = ScanRequest.builder()
                .tableName(tableName)
                .build()

            val result = dynamoDbClient.scan(scanRequest)

            return result.items().map { mapToUserDto(it) }
        } catch (e: DynamoDbException) {
            // Log and return empty list on DynamoDB errors
            log.error("Error scanning DynamoDB table '{}'", tableName, e)
            return emptyList()
        }
    }

    fun findById(id: Int): UserDto? {
        try {
            val key = mapOf("id" to AttributeValue.builder().n(id.toString()).build())
            val request = GetItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .build()

            val response = dynamoDbClient.getItem(request)
            val item = response.item()
            return if (item == null || item.isEmpty()) null else mapToUserDto(item)
        } catch (e: DynamoDbException) {
            log.error("Error getting item from table '{}' for id={}", tableName, id, e)
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
            return true
        } catch (e: DynamoDbException) {
            log.error("Error putting item into table '{}' for id={}", tableName, user.id, e)
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
            return true
        } catch (e: DynamoDbException) {
            log.error("Error deleting item from table '{}' for id={}", tableName, id, e)
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
