package sap.kotlin.maven.client

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.core.ParameterizedTypeReference
import sap.kotlin.maven.model.UserDto
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import sap.kotlin.maven.graphql.CreateUserData
import sap.kotlin.maven.graphql.GraphQLRequest
import sap.kotlin.maven.graphql.GraphQLResponse
import sap.kotlin.maven.graphql.UsersData


@Component
class GraphQLClient {

    private val client = WebClient.create("http://localhost:8080/graphql")

    // Fetch all users
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

    // Create a new user
    suspend fun createUser(id: String, name: String, email: String): UserDto {
        val mutation = """
            mutation CreateUser(${'$'}id: String!, ${'$'}name: String!, ${'$'}email: String!) {
                createUser(id: ${'$'}id, name: ${'$'}name, email: ${'$'}email) {
                    id
                    name
                    email
                }
            }
        """.trimIndent()

        val requestBody = GraphQLRequest(
            query = mutation,
            variables = mapOf(
                "id" to id,
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

        return response.data.createUser
    }
}
