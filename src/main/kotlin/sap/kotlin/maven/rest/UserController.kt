package sap.kotlin.maven.rest


//import org.springframework.graphql.support.DefaultExecutionGraphQlRequest
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestBody
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RequestParam
//import org.springframework.web.bind.annotation.RestController
//import reactor.core.publisher.Mono
//import sap.kotlin.maven.client.GraphQLClient
//import sap.kotlin.maven.model.UserDto
//import java.util.Locale
//
//@RestController
//@RequestMapping("/api")
//class UserController(private val graphQLClient: GraphQLClient) {
//
//    @GetMapping("/users")
//    suspend fun getUsers(): List<UserDto> {
//        return graphQLClient.fetchUsers()
//    }
//}