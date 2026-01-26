package sap.kotlin.maven.entity

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@DynamoDbBean
class UserDynamoEntity {

    @get:DynamoDbPartitionKey
    var id: String? = null

    var name: String? = null

    var email: String? = null
}
