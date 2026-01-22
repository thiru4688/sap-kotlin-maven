package sap.kotlin.maven.graphql


import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import sap.kotlin.maven.model.UserDto
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
//import sap.kotlin.maven.model.UserEntity
//import sap.kotlin.maven.service.UserService
import sap.kotlin.maven.service.UserService1

/*
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
        @Argument name: String,
        @Argument email: String
    ): UserDto {
        return userService.createUser(name, email)
    }

    @MutationMapping
    suspend fun updateUser(
        @Argument id: Long,
        @Argument name: String,
        @Argument email: String
    ): UserEntity {
        return userService.updateUser(id, name, email)
    }

    @MutationMapping
    suspend fun deleteUser(
        @Argument id: Long): String
    {
        return userService.deleteUser(id)
    }
}*/

/*@Controller
class UserGraphQLController(
    private val userService: UserService1
) {

    @QueryMapping
    suspend fun users(): List<UserDto> {
        return userService.getUsers()
    }

    @QueryMapping
    suspend fun getUserById(id: Long): UserDto {
        return userService.getUserById(id)
    }

    @MutationMapping
    suspend fun createUser(
        @Argument name: String,
        @Argument email: String
    ): UserDto {
        return userService.createUser(name, email)
    }
}*/
    @Controller
    class UserGraphQLController(
        private val userService: UserService1
    ) {

        @QueryMapping
        suspend fun users(): List<UserDto> =
            userService.getUsers()

        @QueryMapping
        fun userById(@Argument id: Long): UserDto =
            userService.getUserById(id) // ✅ Uses Redis cache

        @MutationMapping
        suspend fun createUser(
            @Argument name: String,
            @Argument email: String
        ): UserDto =
            userService.createUser(name, email)

        @MutationMapping
        suspend fun updateUser(
            @Argument id: Long,
            @Argument name: String,
            @Argument email: String
        ): UserDto =
            userService.updateUser(id, name, email)

        @MutationMapping
        suspend fun deleteUser(
            @Argument id: Long
        ): String =
            userService.deleteUser(id)
    }
