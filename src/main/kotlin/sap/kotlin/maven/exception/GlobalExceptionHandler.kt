package sap.kotlin.maven.exception

import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    // ---------------- REST Exception Handling ----------------

    @ExceptionHandler(UserNotFoundException::class)
    fun handleRestException(ex: UserNotFoundException): ResponseEntity<ApiError> {

        val error = ApiError(
            status = HttpStatus.NOT_FOUND.value(),
            message = ex.message ?: "User not found"
        )

        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }


    // ---------------- GraphQL Exception Handling ----------------

    @GraphQlExceptionHandler(UserNotFoundException::class)
    fun handleGraphQlException(ex: UserNotFoundException): GraphQLError {

        return GraphqlErrorBuilder.newError()
            .message(ex.message ?: "User not found")
            .build()
    }
}


