package sap.kotlin.maven.graphql


import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import sap.kotlin.maven.model.UserDto
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import sap.kotlin.maven.service.UserService

@Controller
class UserGraphQLController(
    private val service: UserService
) {

    // READ
    @QueryMapping
    fun users(): List<UserDto> =
        service.getAll()

    @QueryMapping
    fun user(@Argument id: String): UserDto =
        service.getById(id)

    // CREATE
    @MutationMapping
    fun createUser(
        @Argument id: String,
        @Argument name: String,
        @Argument email: String
    ): UserDto =
        service.create(UserDto(id, name, email))

    // UPDATE
    @MutationMapping
    fun updateUser(
        @Argument id: String,
        @Argument name: String,
        @Argument email: String
    ): UserDto =
        service.update(id, UserDto(id, name, email))

    // DELETE
    @MutationMapping
    fun deleteUser(@Argument id: String): Boolean {
        service.delete(id)
        return true
    }
}