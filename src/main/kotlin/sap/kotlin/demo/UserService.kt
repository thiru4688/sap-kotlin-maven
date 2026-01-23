package sap.kotlin.demo

import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(private val repo: UserRepository) {

    fun save(name: String, email: String): User =
        repo.save(
            User().apply {
                id = UUID.randomUUID().toString()
                this.name = name
                this.email = email
            }
        )

    fun findById(id: String) = repo.findById(id)
    fun findAll() = repo.findAll()
    fun deleteById(id: String) = repo.deleteById(id)
}
