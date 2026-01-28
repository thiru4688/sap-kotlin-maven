package sap.kotlin.maven.rest


import org.springframework.graphql.support.DefaultExecutionGraphQlRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import sap.kotlin.maven.client.GraphQLClient
import sap.kotlin.maven.model.UserDto
import sap.kotlin.maven.model.UserRequest
import java.util.Locale

@RestController
@RequestMapping("/api")
class UserController(private val graphQLClient: GraphQLClient) {

    @GetMapping("/users")
    suspend fun getUsers(): List<UserDto> {
        return graphQLClient.fetchUsers()
    }

    @GetMapping("/users/{id}")
    suspend fun getUserById(@PathVariable id: String): UserDto? =
        graphQLClient.fetchUserById(id)

    @PostMapping("/users")
    suspend fun createUser(
        @RequestBody request: UserRequest
    ): UserDto =
        graphQLClient.createUser(
            id = request.id,
            name = request.name,
            email = request.email
        )


    @PutMapping("/users/{id}")
    suspend fun updateUser(
        @PathVariable id: String,
        @RequestBody request: UserRequest
    ): UserDto =
        graphQLClient.updateUser(
            id = id,
            name = request.name,
            email = request.email
        )


    @DeleteMapping("/users/{id}")
    suspend fun deleteUser(@PathVariable id: String): String =
        graphQLClient.deleteUser(id)

}