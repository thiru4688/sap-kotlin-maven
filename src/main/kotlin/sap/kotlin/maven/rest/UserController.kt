package sap.kotlin.maven.rest


import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sap.kotlin.maven.model.UserDto
import sap.kotlin.maven.service.UserService

@RestController
@RequestMapping("/api")
class UserController(private val userService: UserService) {

    @GetMapping("/users")
    fun getUsers(): List<UserDto> {
        return userService.findAll()
    }

    @GetMapping("/users/{id}")
    fun getUserById(@PathVariable id: Int): ResponseEntity<UserDto> {
        val user = userService.findById(id)
        return if (user != null) ResponseEntity.ok(user) else ResponseEntity.notFound().build()
    }

    @PostMapping("/users")
    fun createUser(@RequestBody user: UserDto): ResponseEntity<String> {
        val ok = userService.save(user)
        return if (ok) ResponseEntity.status(201).body("User created") else ResponseEntity.status(500).body("Failed to create user")
    }

    @PutMapping("/users/{id}")
    fun updateUser(@PathVariable id: Int, @RequestBody user: UserDto): ResponseEntity<String> {
        if (id != user.id) {
            return ResponseEntity.badRequest().body("ID in path and body must match")
        }

        val ok = userService.save(user)
        return if (ok) ResponseEntity.ok("User updated") else ResponseEntity.status(500).body("Failed to update user")
    }

    @DeleteMapping("/users/{id}")
    fun deleteUser(@PathVariable id: Int): ResponseEntity<String> {
        val ok = userService.deleteById(id)
        return if (ok) ResponseEntity.ok("User deleted") else ResponseEntity.status(500).body("Failed to delete user")
    }
}