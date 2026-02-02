package sap.kotlin.demo

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@Service
class UserReactiveService(private val repository: UserReactiveRepo) {

    fun createUser(name: String, email: String): Mono<User> =
        repository.save(
            User().apply {
                println("Inside save of reactive ")
                id = UUID.randomUUID().toString()
                this.name = name
                this.email = email
                println("Done save using reactive ")
            }
        )

    fun getUser(userId: String): Mono<User> =
        repository.findById(userId)

    fun getUsers(): Flux<User> =
       repository.getUsers()
}