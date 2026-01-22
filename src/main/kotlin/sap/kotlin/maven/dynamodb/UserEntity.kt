package sap.kotlin.maven.dynamodb

/*import io.awspring.cloud.dynamodb.DynamoDbBean
import io.awspring.cloud.dynamodb.DynamoDbPartitionKey*/
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@DynamoDbBean
data class UserEntity(
    @get:DynamoDbPartitionKey
    var id: String? = null,
    var name: String? = null,
    var email: String? = null
)