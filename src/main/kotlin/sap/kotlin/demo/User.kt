package sap.kotlin.demo

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@DynamoDbBean
class User {

    @get:DynamoDbPartitionKey
    lateinit var id: String

    lateinit var name: String
    lateinit var email: String
}
