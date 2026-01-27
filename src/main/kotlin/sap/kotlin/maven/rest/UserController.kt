package sap.kotlin.maven.rest


import org.springframework.graphql.support.DefaultExecutionGraphQlRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import sap.kotlin.maven.client.GraphQLClient
import sap.kotlin.maven.model.UserDto
import sap.kotlin.maven.service.UserService
import java.util.Locale

@RestController
@RequestMapping("/api/users")
class UserController(
    private val service: UserService
) {

    @GetMapping
    fun getAll(): List<UserDto> =
        service.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): UserDto =
        service.getById(id)

    @PostMapping
    fun create(@RequestBody user: UserDto): UserDto =
        service.create(user)

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody user: UserDto
    ): UserDto =
        service.update(id, user)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String) {
        service.delete(id)
    }
}
