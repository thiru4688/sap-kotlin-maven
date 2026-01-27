package sap.kotlin.maven.model

import kotlinx.serialization.Serializable
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import java.time.Instant

@Serializable
data class UserDto(
    val id: String,
    val name: String,
    val email: String
)

@DynamoDbBean
data class User(
    @get:DynamoDbPartitionKey
    @get:DynamoDbAttribute("userId") // <-- MUST match table
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var createdAt: Instant? = null
)
