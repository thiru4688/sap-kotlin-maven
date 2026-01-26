package sap.kotlin.maven.exception

import java.time.LocalDateTime

data class ApiError(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val message: String
)
