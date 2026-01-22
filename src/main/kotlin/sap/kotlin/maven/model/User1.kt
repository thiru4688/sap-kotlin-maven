package sap.kotlin.maven.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@DynamoDbBean
data class User1 (
    @get:DynamoDbPartitionKey // Partition key is required
    var id: Long = 0,
    var name: String = "",
    var email: String = ""
)