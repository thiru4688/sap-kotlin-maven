package sap.kotlin.maven.client

//import kotlinx.coroutines.reactor.awaitSingle
//import sap.kotlin.maven.model.UserDto
//import org.springframework.stereotype.Component
//import org.springframework.web.reactive.function.client.WebClient
//import kotlin.collections.get
//
//@Component
//class GraphQLClient {
//
//    private val client = WebClient.create("http://localhost:8080/graphql")
//
//    suspend fun fetchUsers(): List<UserDto> {
//        val query = """
//            query {
//                users {
//                    id
//                    name
//                    email
//                }
//            }
//        """.trimIndent()
//
//        val response = client.post()
//            .bodyValue(mapOf("query" to query))
//            .retrieve()
//            .bodyToMono(Map::class.java)
//            .awaitSingle()
//
//        val usersList = (response["data"] as Map<*, *>)["users"] as List<Map<String, String>>
//
//        return usersList.map { UserDto(it["id"]!!, it["name"]!!, it["email"]!!) }
//    }
//}