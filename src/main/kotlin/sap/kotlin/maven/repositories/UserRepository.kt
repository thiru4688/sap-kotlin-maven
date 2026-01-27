package sap.kotlin.maven.repositories

import org.springframework.stereotype.Repository
import sap.kotlin.maven.model.User
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema

@Repository
class UserRepository(
    enhancedClient: DynamoDbEnhancedClient
) {

    private val table = enhancedClient.table(
        "users",
        TableSchema.fromBean(User::class.java)
    )

    fun save(user: User) {
        table.putItem(user)
    }



    fun findById(id: String): User? {
        println("DynamoDB findById called with id=[$id]")

        require(id.isNotBlank()) { "Partition key id is blank" }

        val key = Key.builder()
            .partitionValue(id)
            .build()
        if (table.getItem (key) == null) {
            println("User not found for id=$id")
        }
        return table.getItem(key)
    }

    fun delete(id: String) {
        val key = Key.builder()
            .partitionValue(id)
            .build()

        table.deleteItem(key)
    }
}
