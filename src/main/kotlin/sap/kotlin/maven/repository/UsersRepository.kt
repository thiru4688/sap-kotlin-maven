package sap.kotlin.maven.repository


import sap.kotlin.maven.model.Users
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import kotlin.collections.toList
import kotlin.jvm.java

@Repository
class UsersRepository(private val dynamoDbEnhancedClient: DynamoDbEnhancedClient) {

    private val table: DynamoDbTable<Users> =
        dynamoDbEnhancedClient.table("users-info", TableSchema.fromBean(Users::class.java))

    fun save(user: Users) {
        table.putItem(user)
    }

    fun findById(id: Long): Users? {
        return table.getItem { it.key { k -> k.partitionValue(id) } }
    }

    fun findAll(): List<Users> {
        return table.scan().items().toList()
    }

    fun update(user: Users) {
        table.updateItem(user)
    }

    fun delete(id: Long) {
        table.deleteItem { it.key { k -> k.partitionValue(id) } }
    }
}