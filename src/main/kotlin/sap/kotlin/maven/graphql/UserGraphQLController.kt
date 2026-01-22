package sap.kotlin.maven.graphql


import org.springframework.graphql.data.method.annotation.Argument
import sap.kotlin.maven.model.UserDto
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class UserGraphQLController {

    @QueryMapping
    fun users(): List<UserDto> {
        return mockUsers
    }

    //to get single user when given the user id
    @QueryMapping
    fun user(@Argument id: String): UserDto? {
        return mockUsers.find { it.id == id }
    }
}