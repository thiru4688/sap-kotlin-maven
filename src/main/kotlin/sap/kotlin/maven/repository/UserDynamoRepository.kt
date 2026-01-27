package sap.kotlin.maven.repository

import org.springframework.stereotype.Component
import sap.kotlin.maven.model.UserEntity
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.TableSchema

@Component
class UserDynamoRepository(
    enhancedClient: DynamoDbEnhancedClient
) {

    private val table = enhancedClient.table(
        "Users",
        TableSchema.fromBean(UserEntity::class.java)
    )

    fun findAll(): List<UserEntity> =
        table.scan().items().toList()

    fun findById(id: String): UserEntity? =
        table.getItem { it.key { k -> k.partitionValue(id) } }

    fun save(user: UserEntity): UserEntity {
        table.putItem(user)
        return user
    }

    fun deleteById(id: String) {
        table.deleteItem { it.key { k -> k.partitionValue(id) } }
    }
}