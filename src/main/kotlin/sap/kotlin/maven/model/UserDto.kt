package sap.kotlin.maven.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Long,
    val name: String,
    val email: String
): java.io.Serializable

/*
fun UserEntity.toDto() =
    UserDto(
        id = id,
        name = name,
        email = email
    )*/
fun User1.toDto() =
    UserDto(
        id = id,
        name = name,
        email = email
    )