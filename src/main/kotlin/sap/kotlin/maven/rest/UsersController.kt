package sap.kotlin.maven.rest


import sap.kotlin.maven.model.Users
import sap.kotlin.maven.service.UsersService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UsersController(private val userService: UsersService) {

    @PostMapping
    fun createUser(@RequestBody user: Users) {
        userService.createUser(user)
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): Users? = userService.getUser(id)

    @GetMapping
    fun getAllUsers(): List<Users> = userService.getAllUsers()

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody user: Users) {
        // Ensure path id matches body id
        val updatedUser = user.copy(id = id)
        userService.updateUser(updatedUser)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long) {
        userService.deleteUser(id)
    }
}