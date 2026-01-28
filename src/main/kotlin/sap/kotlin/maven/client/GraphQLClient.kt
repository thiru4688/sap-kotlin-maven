package sap.kotlin.maven.client

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import sap.kotlin.maven.model.UserDto

/* ---------- GraphQL transport models ---------- */

data class GraphQLRequest(
    val query: String,
    val variables: Map<String, Any?>? = null
)

data class GraphQLResponse<T>(
    val data: T?,
    val errors: List<GraphQLError>? = null
)

data class GraphQLError(
    val message: String
)

/* ---------- Client ---------- */

@Component
class GraphQLClient {

    private val client = WebClient.create("http://localhost:8080/graphql")

    /* ---------- Queries ---------- */
    @Cacheable("users")
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

        val data = execute(query) ?: return emptyList()
        val users = data["users"] as? List<Map<String, String>> ?: return emptyList()

        return users.map { it.toUserDto() }
    }

    @Cacheable(value = ["user"], key = "#id")
    suspend fun fetchUserById(id: String): UserDto? {
        val query = """
            query (${'$'}id: ID!) {
              userById(id: ${'$'}id) {
                id
                name
                email
              }
            }
        """.trimIndent()

        val data = execute(query, mapOf("id" to id)) ?: return null
        val user = data["userById"] ?: return null

        return (user as Map<String, String>).toUserDto()
    }

    /* ---------- Mutations ---------- */

    @CacheEvict(value = ["users", "user"], allEntries = true)
    suspend fun createUser(id: String, name: String, email: String): UserDto {
        val mutation = """
            mutation (${'$'}id: ID!, ${'$'}name: String!, ${'$'}email: String!) {
              createUser(id: ${'$'}id, name: ${'$'}name, email: ${'$'}email) {
                id
                name
                email
              }
            }
        """.trimIndent()

        val data = execute(
            mutation,
            mapOf("id" to id, "name" to name, "email" to email)
        ) ?: error("Create user failed")

        return (data["createUser"] as Map<String, String>).toUserDto()
    }

    @CacheEvict(value = ["users", "user"], allEntries = true)
    suspend fun updateUser(id: String, name: String, email: String): UserDto {
        val mutation = """
            mutation (${'$'}id: ID!, ${'$'}name: String!, ${'$'}email: String!) {
              updateUser(id: ${'$'}id, name: ${'$'}name, email: ${'$'}email) {
                id
                name
                email
              }
            }
        """.trimIndent()

        val data = execute(
            mutation,
            mapOf("id" to id, "name" to name, "email" to email)
        ) ?: error("Update user failed")

        return (data["updateUser"] as Map<String, String>).toUserDto()
    }

    @CacheEvict(value = ["users", "user"], allEntries = true)
    suspend fun deleteUser(id: String): String {
        val mutation = """
            mutation (${'$'}id: ID!) {
              deleteUser(id: ${'$'}id)
            }
        """.trimIndent()

        val data = execute(
            mutation,
            mapOf("id" to id)
        ) ?: error("Delete user failed")

        return data["deleteUser"] as String
    }

    /* ---------- Internal executor ---------- */

    private suspend fun execute(
        query: String,
        variables: Map<String, Any?>? = null
    ): Map<String, Any>? {

        val response = client.post()
            .bodyValue(GraphQLRequest(query, variables))
            .retrieve()
            .bodyToMono(GraphQLResponse::class.java)
            .awaitSingle()

        if (!response.errors.isNullOrEmpty()) {
            error("GraphQL error: ${response.errors[0].message}")
        }

        @Suppress("UNCHECKED_CAST")
        return response.data as? Map<String, Any>
    }

    /* ---------- Mapper ---------- */

    private fun Map<String, String>.toUserDto() =
        UserDto(
            id = this["id"] ?: "",
            name = this["name"] ?: "",
            email = this["email"] ?: ""
        )
}
