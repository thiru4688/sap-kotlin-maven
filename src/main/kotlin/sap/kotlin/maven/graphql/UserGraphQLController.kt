package sap.kotlin.maven.graphql


import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import sap.kotlin.maven.model.UserDto
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import sap.kotlin.maven.service.UserService


@Controller
class UserGraphQLController(private val userService: UserService) {

    /* -------------------- READ (GET) -------------------- */

    @QueryMapping
    fun users(): List<UserDto> {
        return userService.getAllUsers()
    }

    //to get single user when given the user id
    @QueryMapping
    fun user(@Argument id: String): UserDto? {
        return userService.getUserById(id)
    }

    /* -------------------- CREATE (POST) -------------------- */

    @MutationMapping
    fun createUser(
        @Argument id: String,
        @Argument name: String,
        @Argument email: String
    ): UserDto {
        return userService.createUser(
            UserDto(id, name, email)
        )
    }

    /* -------------------- UPDATE (PUT) -------------------- */

    @MutationMapping
    fun updateUser(
        @Argument id: String,
        @Argument name: String,
        @Argument email: String
    ): UserDto {
        return userService.updateUser(
            UserDto(id, name, email)
        )
    }

    /* -------------------- DELETE -------------------- */

    @MutationMapping
    fun deleteUser(@Argument id: String): Boolean {
        return userService.deleteUser(id)
    }
}