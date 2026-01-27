package sap.kotlin.maven.client

import sap.kotlin.maven.model.UserDto
import org.springframework.stereotype.Component
import sap.kotlin.maven.service.UserService

@Component
class GraphQLClient(private val userService: UserService) {

    suspend fun fetchUsers(): List<UserDto> {
        return userService.fetchUsers()
    }
}