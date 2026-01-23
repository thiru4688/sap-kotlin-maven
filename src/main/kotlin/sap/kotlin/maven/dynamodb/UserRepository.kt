package sap.kotlin.maven.dynamodb

import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.TableSchema

@Repository
class UserRepository(
    enhancedClient: DynamoDbEnhancedClient
) {

    private val table = enhancedClient.table(
        "users",   // ✅ TABLE NAME HERE
        TableSchema.fromBean(UserEntity::class.java)
    )

    fun findAll(): List<UserEntity> =
        table.scan().items().toList()

    fun findById(id: String): UserEntity? =
        table.getItem { it.key { k -> k.partitionValue(id) } }

    fun save(user: UserEntity) {
        table.putItem(user)
    }

    fun deleteById(id: String) {
        table.deleteItem { it.key { k -> k.partitionValue(id) } }
    }
}
