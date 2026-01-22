package sap.kotlin.maven.client

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.core.ParameterizedTypeReference
import sap.kotlin.maven.model.UserDto
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import sap.kotlin.maven.graphql.CreateUserData
import sap.kotlin.maven.graphql.DeleteUserData
import sap.kotlin.maven.graphql.GraphQLRequest
import sap.kotlin.maven.graphql.GraphQLResponse
import sap.kotlin.maven.graphql.UpdateUserData
import sap.kotlin.maven.graphql.UserData
import sap.kotlin.maven.graphql.UsersData
//import sap.kotlin.maven.model.UserEntity


@Component
class GraphQLClient {

    private val client = WebClient.create("http://localhost:8080/graphql")

    /* ---------------- FETCH USERS ---------------- */
    suspend fun fetchUsers(): List<UserDto> {
        val query = """
            query {
                users {
                    id
                    name
                    email
                }
            }
        """.trimIndent()

        val request = GraphQLRequest(query)

        val response: GraphQLResponse<UsersData> =
            client.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(object : ParameterizedTypeReference<GraphQLResponse<UsersData>>() {})
                .awaitSingle()

        response.errors?.let { throw RuntimeException(it.joinToString { it.message }) }

        return response.data?.users ?: emptyList()
    }

    /* ---------------- GET USER BY ID---------------- */

    suspend fun fetchUserById(id: Long): UserDto {
        val query = """
        query(${'$'}id: ID!) {
            userById(id: ${'$'}id) {
                id
                name
                email
            }
        }
    """.trimIndent()

        // Pass the ID as a variable map
        val request = GraphQLRequest(
            query = query,
            variables = mapOf("id" to id)
        )

        val response: GraphQLResponse<UserData> =
            client.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(object : ParameterizedTypeReference<GraphQLResponse<UserData>>() {})
                .awaitSingle()

        response.errors?.let { errors ->
            throw RuntimeException("GraphQL Error: " + errors.joinToString { it.message })
        }

        return response.data?.userById ?: throw RuntimeException("User not found in GraphQL response")
    }

    /* ---------------- CREATE USER ---------------- */
    suspend fun createUser(name: String, email: String): UserDto {
        val mutation = """
            mutation CreateUser(${'$'}name: String!, ${'$'}email: String!) {
                createUser(name: ${'$'}name, email: ${'$'}email) {
                    id
                    name
                    email
                }
            }
        """.trimIndent()

        val requestBody = GraphQLRequest(
            query = mutation,
            variables = mapOf(
                "name" to name,
                "email" to email
            )
        )

        val response: GraphQLResponse<CreateUserData> =
            client.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(object : ParameterizedTypeReference<GraphQLResponse<CreateUserData>>() {})
                .awaitSingle()

        response.errors?.let { throw RuntimeException(it.joinToString { it.message }) }

        return response.data?.createUser ?: throw RuntimeException("User not found in GraphQL response")
    }

    /* ---------------- UPDATE USER ---------------- */

    suspend fun updateUser(id: Long, name: String, email: String): UserDto {
        val mutation = """
            mutation UpdateUser(${'$'}id: ID!, ${'$'}name: String!, ${'$'}email: String!) {
                updateUser(id: ${'$'}id, name: ${'$'}name, email: ${'$'}email) {
                    id
                    name
                    email
                }
            }
        """.trimIndent()

        val response: GraphQLResponse<UpdateUserData> =
            client.post()
                .bodyValue(
                    GraphQLRequest(
                        query = mutation,
                        variables = mapOf(
                            "id" to id,
                            "name" to name,
                            "email" to email
                        )
                    )
                )
                .retrieve()
                .bodyToMono(object :
                    ParameterizedTypeReference<GraphQLResponse<UpdateUserData>>() {})
                .awaitSingle()

        response.errors?.let {
            throw RuntimeException(it.joinToString { e -> e.message })
        }

        return response.data?.updateUser  ?: throw RuntimeException("User not found in GraphQL response")
    }

    /* ---------------- DELETE USER ---------------- */

    suspend fun deleteUser(id: Long): String {
        val mutation = """
            mutation DeleteUser(${'$'}id: ID!) {
                deleteUser(id: ${'$'}id)
            }
        """.trimIndent()

        val response: GraphQLResponse<DeleteUserData> =
            client.post()
                .bodyValue(
                    GraphQLRequest(
                        query = mutation,
                        variables = mapOf("id" to id)
                    )
                )
                .retrieve()
                .bodyToMono(object :
                    ParameterizedTypeReference<GraphQLResponse<DeleteUserData>>() {})
                .awaitSingle()

        if (!response.errors.isNullOrEmpty()) {
            val errorMessage = response.errors.first().message
            throw RuntimeException(errorMessage)
        }

        return response.data?.deleteUser  ?: throw RuntimeException("User not found in GraphQL response")
    }
}
