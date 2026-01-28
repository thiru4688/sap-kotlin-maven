package sap.kotlin.maven.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
//import java.io.Serializable

@DynamoDbBean
data class Users (
    @get:DynamoDbPartitionKey // Partition key is required
    var id: Long = 0,
    var name: String? = "",
    var email: String = ""
)