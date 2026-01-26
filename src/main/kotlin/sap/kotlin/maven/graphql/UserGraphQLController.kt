package sap.kotlin.maven.graphql

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import sap.kotlin.maven.entity.UserDynamoEntity
import sap.kotlin.maven.exception.UserNotFoundException
import sap.kotlin.maven.service.UserService

@org.springframework.stereotype.Controller
class UserGraphQLController(private val userService: UserService) {

    // -------- QUERY --------

    @QueryMapping
    fun getAllUsers(): List<UserDynamoEntity> {
        return userService.getAllUsers()
    }

    @QueryMapping
    fun getUserById(@Argument id: String): UserDynamoEntity {

        return userService.getUserById(id)
            ?: throw UserNotFoundException("User with id $id not found")
    }

    // -------- MUTATION --------

    @MutationMapping
    fun createUser(@Argument input: UserDynamoEntity): UserDynamoEntity {
        return userService.createUser(input)
    }

    @MutationMapping
    fun updateUser(@Argument input: UserDynamoEntity): UserDynamoEntity {

        val userId = requireNotNull(input.id) {
            "ID must be provided for update"
        }

        val existing = userService.getUserById(userId)
            ?: throw UserNotFoundException("User not found for update")

        input.id = userId

        return userService.updateUser(input)
    }

    @MutationMapping
    fun deleteUser(@Argument id: String): Boolean {

        val deleted = userService.deleteUser(id)

        if (!deleted) {
            throw UserNotFoundException("User not found for delete")
        }

        return true
    }
}








/*
                           In-memory approach

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import sap.kotlin.maven.model.UserDto
import sap.kotlin.maven.service.UserService

@Controller
class UserGraphQLController(private val userService: UserService) {

    @QueryMapping
    fun users(): List<UserDto> =
        userService.getAllUsers()

    @QueryMapping
    fun userById(@Argument id: String): UserDto =
        userService.getUserById(id)

    @MutationMapping
    fun createUser(
        @Argument id: String,
        @Argument name: String,
        @Argument email: String
    ): UserDto =
        userService.createUser(UserDto(id, name, email))

    @MutationMapping
    fun updateUser(
        @Argument id: String,
        @Argument name: String,
        @Argument email: String
    ): UserDto =
        userService.updateUser(UserDto(id, name, email))

    @MutationMapping
    fun deleteUser(@Argument id: String): String {
        userService.deleteUser(id)
        return "User with following id has been successfully deleted"
    }
}


 */