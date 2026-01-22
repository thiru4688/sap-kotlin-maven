package sap.kotlin.maven.rest


import org.springframework.web.bind.annotation.*
import sap.kotlin.maven.client.GraphQLClient
import sap.kotlin.maven.model.UserDto
import sap.kotlin.maven.model.UsersDto
import sap.kotlin.maven.service.UserService

@RestController
@RequestMapping("/users")
class UserController(private val graphQLClient: GraphQLClient,
                     private val service: UserService) {

    @GetMapping("/mock-data")
    suspend fun getUsers(): List<UserDto> {
        return graphQLClient.fetchUsers()
    }

    @GetMapping
    fun getAllUsers(): List<UsersDto> = service.getAllUsers()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Int): UsersDto? = service.getUserById(id)

    @PostMapping
    fun createUser(@RequestBody user: UsersDto): UsersDto = service.createUser(user)

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Int, @RequestBody updated: Map<String, String>): UsersDto? {
        val name = updated["name"]
        val email = updated["email"]
        return service.updateUser(id, UsersDto(id = id, name = name ?: "", email = email ?: ""))
    }


    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Int): Boolean = service.deleteUser(id)

}