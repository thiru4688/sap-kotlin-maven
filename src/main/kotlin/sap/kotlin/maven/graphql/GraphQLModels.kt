package sap.kotlin.maven.graphql

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable
import sap.kotlin.maven.model.UserDto
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@Serializable
data class GraphQLRequest(
    val query: String,
    val variables: Map<String, Any>? = null
)

@Serializable
data class GraphQLResponse<T>(
    val data: T?,
    val errors: List<GraphQLError>? = null
)

@Serializable
data class GraphQLError(
    val message: String
)

@Serializable
data class UsersData(
    val users: List<UserDto> = emptyList()
)

@Serializable
data class CreateUserData(
    val createUser: UserDto
)

@Serializable
data class UserRequest(
    val name: String,
    val email: String
)

@Serializable
data class UpdateUserData(val updateUser: UserDto)

@Serializable
data class DeleteUserData(val deleteUser: String)
@Serializable
data class UserData(
    val userById: UserDto?
)

@DynamoDbBean
data class UserEntity(
    @get:DynamoDbPartitionKey
    var id: Long = 0,
    var name: String = "",
    var email: String = ""
)