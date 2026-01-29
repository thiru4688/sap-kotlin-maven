package sap.kotlin.demo

import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest

@Repository
class UserReactiveRepo( val dynamoDb: DynamoDbAsyncClient) {
    private val tableName = "users"
    fun save(user: User): Mono<User> {
        val item = mapOf(
            "id" to AttributeValue.fromS(user.id),
            "name" to AttributeValue.fromS(user.name),
            "email" to AttributeValue.fromS(user.email)
        )
        println("Inside save of reactive repo ")

        val request = PutItemRequest.builder()
            .tableName(tableName)
            .item(item)
            .build()

        return Mono.fromFuture(dynamoDb.putItem(request))
            .thenReturn(user)
    }

    fun findById(userId: String): Mono<User> {
        val request = GetItemRequest.builder()
            .tableName(tableName)
            .key(mapOf("id" to AttributeValue.fromS(userId)))
            .build()
        println("Inside save of reactive repo ")
        return Mono.fromFuture(dynamoDb.getItem(request))
            .flatMap { response ->
                if (!response.hasItem()) Mono.empty<User>()
                else Mono.just(
                    User().apply {
                        id = userId
                        name = response.item()["name"]!!.s()
                        email = response.item()["email"]!!.s()
                    }
                )
            }
    }
}
