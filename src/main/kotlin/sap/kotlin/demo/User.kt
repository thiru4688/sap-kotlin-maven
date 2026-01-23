package sap.kotlin.demo

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import java.util.UUID

//import jakarta.persistence.*
//
//@Entity
//@Table(name = "users")
//data class User(
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Long = 0,
//
//    val name: String,
//    val email: String
//)

@DynamoDbBean
class User {

    @get:DynamoDbPartitionKey
    var id: String? = null

    var name: String? = null
    var email: String? = null
}
