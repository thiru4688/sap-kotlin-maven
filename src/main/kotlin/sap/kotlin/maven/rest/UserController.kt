package sap.kotlin.maven.rest


import org.springframework.graphql.support.DefaultExecutionGraphQlRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import sap.kotlin.maven.client.GraphQLClient
import sap.kotlin.maven.model.User
import sap.kotlin.maven.model.UserDto
import sap.kotlin.maven.service.UserService
import java.util.Locale

@RestController
@RequestMapping("/api")
class UserController(private val graphQLClient: GraphQLClient) {

    @GetMapping("/users")
    suspend fun getUsers(): List<UserDto> {
        return graphQLClient.fetchUsers()
    }
}

@RestController
@RequestMapping("/api/users")
class UserRestController(
    private val userService: UserService
) {

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: String): ResponseEntity<User> =
        userService.getUser(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
}
