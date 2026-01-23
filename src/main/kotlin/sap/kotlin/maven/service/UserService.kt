package sap.kotlin.maven.service

import org.springframework.stereotype.Service
import sap.kotlin.maven.model.User
import sap.kotlin.maven.repositories.UserRepository
import java.time.Instant

@Service
class UserService(
    private val repository: UserRepository
) {

    fun getUser(id: String): User? =
        repository.findById(id)

    fun createUser(id: String, name: String, email: String): User =
        User(
            id = id,
            name = name,
            email = email,
            createdAt = Instant.now()
        ).also(repository::save)
}
