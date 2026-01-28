package sap.kotlin.maven.rest


import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import sap.kotlin.maven.model.Users
import sap.kotlin.maven.service.UsersService

@Controller
class UsersGraphQlControllerAws(private val userService: UsersService) {

    @QueryMapping
    fun userById(@Argument id: Long): Users? {
        return userService.getUser(id)
    }

    @QueryMapping
    fun allUsers(): List<Users> {
        return userService.getAllUsers()
    }

    @MutationMapping
    fun createUser(@Argument id: Long, @Argument email: String, @Argument name: String?): Users {
        val user = Users(id = id, email = email, name = name)
        userService.createUser(user)
        return user
    }

    @MutationMapping
    fun updateUser(@Argument id: Long, @Argument email: String, @Argument name: String?): Users {
        val user = Users(id = id, email = email, name = name)
        userService.updateUser(user)
        return user
    }

    @MutationMapping
     fun deleteUser(@Argument id: Long): Boolean {
        userService.deleteUser(id)
        return true
    }
}