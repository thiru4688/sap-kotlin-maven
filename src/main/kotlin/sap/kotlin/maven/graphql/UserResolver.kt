package sap.kotlin.maven.graphql

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import sap.kotlin.maven.model.User
import sap.kotlin.maven.service.UserService

@Controller
class UserQuery(
    private val service: UserService
) {

    @QueryMapping
    fun user(@Argument id: String): User? =
        service.getUser(id)
}

@Controller
class UserMutation(
    private val service: UserService
) {

    @MutationMapping
    fun createUser(
        @Argument id: String,
        @Argument name: String,
        @Argument email: String
    ): User =
        service.createUser(id, name, email)
}
