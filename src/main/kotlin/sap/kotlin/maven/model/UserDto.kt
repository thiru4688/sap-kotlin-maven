package sap.kotlin.maven.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val name: String,
    val email: String
)
fun UserEntity.toDto() =
    UserDto(id, name, email)