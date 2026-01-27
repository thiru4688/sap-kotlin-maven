package sap.kotlin.maven.model

import kotlinx.serialization.Serializable
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@Serializable
data class UserDto(
    val id: String,
    val name: String,
    val email: String
)


@DynamoDbBean
data class UserEntity(
    @get:DynamoDbPartitionKey
    var id: String? = null,
    var name: String? = null,
    var email: String? = null
)

fun UserEntity.toDto() =
    UserDto(
        id = id!!,
        name = name!!,
        email = email!!
    )

fun UserDto.toEntity() =
    UserEntity(
        id = id,
        name = name,
        email = email
    )
