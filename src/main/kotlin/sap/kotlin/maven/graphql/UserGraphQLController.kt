package sap.kotlin.maven.graphql


import sap.kotlin.maven.model.UserDto
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import sap.kotlin.maven.service.UserService

@Controller
class UserGraphQLController(private val userService: UserService) {

    @QueryMapping
    fun users(): List<UserDto> {
        return userService.fetchUsers()
    }
}