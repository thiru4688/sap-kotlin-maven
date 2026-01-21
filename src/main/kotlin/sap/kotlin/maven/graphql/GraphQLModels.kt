package sap.kotlin.maven.graphql

import kotlinx.serialization.Serializable
import sap.kotlin.maven.model.UserDto

@Serializable
data class GraphQLRequest(
    val query: String,
    val variables: Map<String, Any>? = null
)

@Serializable
data class GraphQLResponse<T>(
    val data: T,
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
