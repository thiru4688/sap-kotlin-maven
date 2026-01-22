package sap.kotlin.maven.rest



import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sap.kotlin.maven.client.GraphQLClient
import sap.kotlin.maven.graphql.UserRequest
import sap.kotlin.maven.model.User1
import sap.kotlin.maven.model.UserDto
//import sap.kotlin.maven.model.UserEntity

@RestController
@RequestMapping("/api")
class UserController(private val graphQLClient: GraphQLClient) {

    @GetMapping("/users")
    suspend fun getUsers(): List<UserDto> {
        return graphQLClient.fetchUsers()
    }

    @GetMapping("/users/{id}")
    suspend fun getUserById(@PathVariable id: Long): UserDto {
        return graphQLClient.fetchUserById(id)
    }

    @PostMapping("/createUser")
    suspend fun createUser(
        @RequestBody request: UserRequest
    ): UserDto {
        return graphQLClient.createUser(
            request.name,
            request.email,
        )
    }

    @PutMapping("/updateUser")
    suspend fun updateUser(
        @RequestBody request: User1
    ): UserDto {
        return graphQLClient.updateUser(
            request.id,
            request.name,
            request.email
        )
    }

    @DeleteMapping("/deleteUser/{id}")
    suspend fun deleteUser(@PathVariable id: Long): String {
        return graphQLClient.deleteUser(id)
    }
}