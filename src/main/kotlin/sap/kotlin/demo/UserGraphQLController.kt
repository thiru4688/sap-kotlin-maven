package sap.kotlin.demo

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class UserGraphQLController(
    private val userRepository: UserRepository
) {

    @QueryMapping
    fun users(): List<User> =
        userRepository.findAll()

    @QueryMapping
    fun userById(@Argument id: Long): User? =
        userRepository.findById(id).orElse(null)

    @MutationMapping
    fun createUser(
        @Argument name: String,
        @Argument email: String
    ): User =
        userRepository.save(User(name = name, email = email))

    @MutationMapping
    fun updateUser(
        @Argument id: Long,
        @Argument name: String,
        @Argument email: String
    ): User {
        val user = userRepository.findById(id)
            .orElseThrow { RuntimeException("User not found") }

        val updatedUser = user.copy(name = name, email = email)
        return userRepository.save(updatedUser)
    }

    @MutationMapping
    fun deleteUser(@Argument id: Long): Boolean {
        userRepository.deleteById(id)
        return true
    }
}
