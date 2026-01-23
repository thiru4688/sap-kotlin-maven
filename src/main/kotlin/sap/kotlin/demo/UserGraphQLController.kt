package sap.kotlin.demo

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class UserGraphQLController(
//    private val userRepository: UserRepository
    private val userService: UserService
) {

    @QueryMapping
    fun users(): List<User> =
        userService.findAll()

    @QueryMapping
    fun userById(@Argument id: String): User? =
        userService.findById(id)

    @MutationMapping
    fun createUser(
        @Argument name: String,
        @Argument email: String
    ): User =
        userService.save(name = name, email = email)

//    @MutationMapping
//    fun updateUser(
//        @Argument id: Long,
//        @Argument name: String,
//        @Argument email: String
//    ): User {
//        val user = userService.findById(id)
//            .orElseThrow { RuntimeException("User not found") }
//
//        val updatedUser = user.copy(name = name, email = email)
//        return userRepository.save(updatedUser)
//    }

    @MutationMapping
    fun deleteUser(@Argument id: String): Boolean {
        userService.deleteById(id)
        return true
    }
}
