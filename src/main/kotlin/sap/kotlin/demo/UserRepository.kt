package sap.kotlin.demo

import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.TableSchema


@Repository
class UserRepository(
    enhancedClient: DynamoDbEnhancedClient
) {

    private val table = enhancedClient.table(
        "users",
        TableSchema.fromBean(User::class.java)
    )

    fun save(user: User): User {
        table.putItem(user)
        return user
    }

    fun findById(id: String): User? =
        table.getItem { it.key { k -> k.partitionValue(id) } }

    fun deleteById(id: String) {
        table.deleteItem { it.key { k -> k.partitionValue(id) } }
    }

    fun findAll(): List<User> =
        table.scan().items().toList()
}

