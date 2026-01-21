package sap.kotlin.maven.graphql


import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import sap.kotlin.maven.model.UserDto
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import sap.kotlin.maven.service.UserService

@Controller
class UserGraphQLController(
    private val userService: UserService
) {

    @QueryMapping
    fun users(): List<UserDto> {
        return userService.getUsers()
    }

    @MutationMapping
    suspend fun createUser(
        @Argument id: String,
        @Argument name: String,
        @Argument email: String
    ): UserDto {
        return userService.createUser(id, name, email)

    }
}