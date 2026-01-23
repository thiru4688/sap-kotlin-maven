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
import sap.kotlin.maven.service.UserService
import java.util.Locale

@RestController
@RequestMapping("/api")
class UserController(private val userService: UserService) {

    @GetMapping("/users")
    fun getUsers(): List<UserDto> {
        return userService.getAllUsers()
    }

    @GetMapping("/users/{id}")
    fun getUserById(@PathVariable id: String): UserDto? {
        return userService.getUserById(id)
    }


    // ✅ POST (create)
    @PostMapping("/users")
    fun createUser(@RequestBody user: UserDto): UserDto =
        userService.createUser(user)

    // ✅ PUT (update)
    @PutMapping("/users/{id}")
    fun updateUser(
        @PathVariable id: String,
        @RequestBody user: UserDto
    ): UserDto? =
        userService.updateUser( user)

    // ✅ DELETE
    @DeleteMapping("/users/{id}")
    fun deleteUser(@PathVariable id: String): String =
        if (userService.deleteUser(id))
            "User deleted successfully"
        else
            "User not found"
}