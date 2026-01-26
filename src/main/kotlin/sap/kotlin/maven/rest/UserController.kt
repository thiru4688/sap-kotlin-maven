package sap.kotlin.maven.rest

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sap.kotlin.maven.entity.UserDynamoEntity
import sap.kotlin.maven.exception.UserNotFoundException
import sap.kotlin.maven.service.UserService

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    // ---------------- CREATE ----------------

    @PostMapping
    fun createUser(@RequestBody user: UserDynamoEntity): UserDynamoEntity {
        return userService.createUser(user)
    }

    // ---------------- GET ALL ----------------

    @GetMapping
    fun getAllUsers(): List<UserDynamoEntity> {
        return userService.getAllUsers()
    }

    // ---------------- GET BY ID ----------------

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: String): UserDynamoEntity {
        return userService.getUserById(id)
            ?: throw UserNotFoundException("User with id $id not found")
    }

    // ---------------- UPDATE ----------------

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: String,
        @RequestBody user: UserDynamoEntity
    ): UserDynamoEntity {

        val existingUser = userService.getUserById(id)
            ?: throw UserNotFoundException("User not found for update")

        user.id = existingUser.id   // ensure ID consistency

        return userService.updateUser(user)
    }

    // ---------------- DELETE ----------------

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String): String {

        val deleted = userService.deleteUser(id)

        if (!deleted) {
            throw UserNotFoundException("User with id $id not found")
        }

        return "User with id $id deleted successfully"
    }
}


/*                       In memory approach

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sap.kotlin.maven.model.UserDto
import sap.kotlin.maven.service.UserService

@RestController
@RequestMapping("/api")
class UserController(private val userService: UserService) {

    @GetMapping("/users")
    fun getUsers(): List<UserDto> {
        return userService.getAllUsers()
    }

    @GetMapping("/users/{id}")
    fun getUserById(@PathVariable id: String): UserDto {
        return userService.getUserById(id)
    }

    @PostMapping("/users")
    fun createUser(@RequestBody user: UserDto): UserDto {
        return userService.createUser(user)
    }

    @PutMapping("/users/{id}")
    fun updateUser(@PathVariable id: String,
                   @RequestBody user: UserDto): UserDto {
        return userService.updateUser(user.copy(id = id))
    }

    @DeleteMapping("/users/{id}")
    fun deleteUser(@PathVariable id: String): String {
        userService.deleteUser(id)
        return "User with following id has been successfully deleted"
    }
}

 */