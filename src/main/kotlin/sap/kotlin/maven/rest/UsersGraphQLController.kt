//package sap.kotlin.maven.rest
//
//import org.springframework.graphql.data.method.annotation.*
//import org.springframework.stereotype.Controller
//import sap.kotlin.maven.model.UsersDto
//import sap.kotlin.maven.service.UserService
//
//@Controller
//class UsersGraphQLController(private val service: UserService) {
//
//    @QueryMapping
//    fun users(): List<UsersDto> = service.getAllUsers()
//
//    @QueryMapping
//    fun userById(@Argument id: Int): UsersDto? = service.getUserById(id)
//
//    @MutationMapping
//    fun createUser(@Argument id: Int, @Argument name: String, @Argument email: String): UsersDto =
//        service.createUser(UsersDto(id = id, name = name, email = email))
//
//    @MutationMapping
//    fun updateUser(
//        @Argument id: Int,
//        @Argument name: String?,
//        @Argument email: String?
//    ): UsersDto? {
//        return service.updateUser(
//            id,
//            UsersDto(id = id, name = name ?: "", email = email ?: "")
//        )
//    }
//
//    @MutationMapping
//    fun deleteUser(@Argument id: Int): Boolean = service.deleteUser(id)
//}
