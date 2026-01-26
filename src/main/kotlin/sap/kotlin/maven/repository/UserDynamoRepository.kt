package sap.kotlin.maven.repository

import sap.kotlin.maven.entity.UserDynamoEntity
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.Key

@Repository
class UserDynamoRepository(
    private val enhancedClient: DynamoDbEnhancedClient
) {

    private val table: DynamoDbTable<UserDynamoEntity> =
        enhancedClient.table(
            "users",   //  EXACT DynamoDB table name
            TableSchema.fromBean(UserDynamoEntity::class.java)
        )

    // ---------------- CREATE / UPDATE ----------------

    fun save(user: UserDynamoEntity): UserDynamoEntity {
        table.putItem(user)
        return user
    }

    // ---------------- GET BY ID ----------------

    fun findById(id: String): UserDynamoEntity? {

        val key = Key.builder()
            .partitionValue(id)
            .build()

        return table.getItem(key)
    }

    // ---------------- GET ALL ----------------

    fun findAll(): List<UserDynamoEntity> {

        val result = mutableListOf<UserDynamoEntity>()

        val pages = table.scan()

        pages.items().forEach {
            result.add(it)
        }

        return result
    }

    // ---------------- DELETE ----------------

    fun deleteById(id: String): Boolean {

        val key = Key.builder()
            .partitionValue(id)
            .build()

        val deletedItem = table.deleteItem(key)

        return deletedItem != null
    }
}
