package sap.kotlin.maven.graphql


import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import sap.kotlin.maven.model.UserDto
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import sap.kotlin.maven.repository.UserRepository
import sap.kotlin.maven.service.UserService

@Controller
class UserGraphQLController(val userService: UserService) {

    @QueryMapping
    fun users(): List<UserDto> = userService.getUsers()

    @QueryMapping
    fun userById(@Argument id: String): UserDto? =
        userService.getById(id)

    @MutationMapping
    suspend fun createUser(
        @Argument id: String,
        @Argument name: String,
        @Argument email: String
    ): UserDto =
        userService.createUser(id,name, email)

    @MutationMapping
    suspend fun updateUser(
        @Argument id: String,
        @Argument name: String,
        @Argument email: String
    ): UserDto =
        userService.updateUser(id, name, email)

    @MutationMapping
    suspend fun deleteUser(
        @Argument id: String
    ): Boolean =
        userService.deleteUser(id)
}