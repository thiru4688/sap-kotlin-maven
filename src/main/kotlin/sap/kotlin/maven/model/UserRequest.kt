package sap.kotlin.maven.model

import kotlinx.serialization.Serializable


@Serializable
data class UserRequest (
    val id: String,
    val name: String,
    val email: String
)