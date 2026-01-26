

/*
                           In-memory repository
package sap.kotlin.maven.repository

import org.springframework.stereotype.Repository
import sap.kotlin.maven.model.UserDto
import java.util.concurrent.ConcurrentHashMap

@Repository
class UserRepository {

    private val users = ConcurrentHashMap<String, UserDto>()

    init {
        // Initial mock data
        users["1"] = UserDto("1", "Ajay", "ajay@test.com")
        users["2"] = UserDto("2", "Kumar", "kumar@test.com")
        users["3"] = UserDto("3", "Methuku", "methuku@test.com")
    }

    fun findAll(): List<UserDto> {
        return users.values.toList()
    }

    fun findById(id: String): UserDto? {
        return users[id]
    }

    fun save(user: UserDto): UserDto {
        users[user.id] = user
        return user
    }

    fun delete(id: String) {
        users.remove(id)
    }
}


 */