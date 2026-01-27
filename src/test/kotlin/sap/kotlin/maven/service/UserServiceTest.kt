package sap.kotlin.maven.service

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito.*
import sap.kotlin.maven.model.UserDto
import sap.kotlin.maven.repository.UserRepository

class UserServiceTest {

    @Test
    fun `fetchUsers returns repository results`() {
        val repo = mock(UserRepository::class.java)
        val service = UserService(repo)

        val users = listOf(UserDto("1", "Alice", "alice@example.com"))

        `when`(repo.findAll()).thenReturn(users)

        val result = service.fetchUsers()

        assertEquals(users, result)
        verify(repo).findAll()
    }
}
