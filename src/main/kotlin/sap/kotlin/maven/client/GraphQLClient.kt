
/*

============We are not using this anymore bcs we are not following REST->GraphQL approach =======


 */








//package sap.kotlin.maven.client
//
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
//
//    suspend fun fetchUserById(id: String): UserDto? {
//
//        val query = """
//        query {
//            userById(id: "$id") {
//                id
//                name
//                email
//            }
//        }
//    """.trimIndent()
//
//        val response = client.post()
//            .bodyValue(mapOf("query" to query))
//            .retrieve()
//            .bodyToMono(Map::class.java)
//            .awaitSingle()
//
//        val userMap = ((response["data"] as Map<*, *>)["userById"]) as Map<String, String>?
//
//        return userMap?.let {
//            UserDto(it["id"]!!, it["name"]!!, it["email"]!!)
//        }
//    }
//
//
//    suspend fun createUser(user: UserDto): UserDto {
//
//        val mutation = """
//        mutation {
//          createUser(
//            id: "${user.id}",
//            name: "${user.name}",
//            email: "${user.email}"
//          ) {
//            id
//            name
//            email
//          }
//        }
//    """.trimIndent()
//
//        val response = client.post()
//            .bodyValue(mapOf("query" to mutation))
//            .retrieve()
//            .bodyToMono(Map::class.java)
//            .awaitSingle()
//
//        val userMap =
//            ((response["data"] as Map<*, *>)["createUser"] as Map<String, String>)
//
//        return UserDto(
//            userMap["id"]!!,
//            userMap["name"]!!,
//            userMap["email"]!!
//        )
//    }
//
//    suspend fun updateUser(user: UserDto): UserDto {
//
//        val mutation = """
//        mutation {
//          updateUser(
//            id: "${user.id}",
//            name: "${user.name}",
//            email: "${user.email}"
//          ) {
//            id
//            name
//            email
//          }
//        }
//    """.trimIndent()
//
//        val response = client.post()
//            .bodyValue(mapOf("query" to mutation))
//            .retrieve()
//            .bodyToMono(Map::class.java)
//            .awaitSingle()
//
//        val userMap =
//            ((response["data"] as Map<*, *>)["updateUser"] as Map<String, String>)
//
//        return UserDto(
//            userMap["id"]!!,
//            userMap["name"]!!,
//            userMap["email"]!!
//        )
//    }
//
//    suspend fun deleteUser(id: String): Boolean {
//
//        val mutation = """
//        mutation {
//          deleteUser(id: "$id")
//        }
//    """.trimIndent()
//
//        val response = client.post()
//            .bodyValue(mapOf("query" to mutation))
//            .retrieve()
//            .bodyToMono(Map::class.java)
//            .awaitSingle()
//
//        val result =
//            (response["data"] as Map<*, *>)["deleteUser"] as Boolean
//
//        return result
//    }
//
//
//
//}