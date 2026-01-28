package sap.kotlin.maven.repository

import org.springframework.stereotype.Repository
import sap.kotlin.maven.entity.UserEntity
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.Key


@Repository
class UserRepository (enhancedClient: DynamoDbEnhancedClient){
    private val table = enhancedClient.table(
        "Users",
        TableSchema.fromBean(UserEntity::class.java)
    )

    fun findAll(): List<UserEntity> =
        table.scan().items().toList()

    fun findById(id: String): UserEntity? =
        table.getItem(Key.builder().partitionValue(id).build())

    fun save(user: UserEntity): UserEntity {
        table.putItem(user)
        return user
    }

    fun delete(id: String): Boolean {
        table.deleteItem(Key.builder().partitionValue(id).build())
        return true
    }

}