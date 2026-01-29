package sap.kotlin.demo

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono

@Controller
class UserReactiveController(private val service: UserReactiveService) {

    @QueryMapping(name = "getUser")
    fun getUser(@Argument id: String): Mono<User> =
        service.getUser(id)

    @MutationMapping(name = "createReactiveUser")
    fun createReactiveUser(@Argument name: String,
        @Argument email: String
    ): Mono<User> {
        println("🚀 resolver method entered")
       return service.createUser(name, email)
            .doOnSubscribe { println("🔥 mutation called") }
            .doOnNext { println("✅ saved user: $it") }
    }
}

