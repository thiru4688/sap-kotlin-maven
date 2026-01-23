package sap.kotlin.maven.DynamoDBInitializer

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import software.amazon.awssdk.services.dynamodb.model.*
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition
import software.amazon.awssdk.services.dynamodb.model.BillingMode
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement
import software.amazon.awssdk.services.dynamodb.model.KeyType
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType
import software.amazon.awssdk.services.dynamodb.model.*
@Component
class DynamoDbTableInitializer(
    private val dynamoDbClient: DynamoDbClient
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        val tableName = "users"

        val tables = dynamoDbClient.listTables().tableNames()
        if (tables.contains(tableName)) {
            println("✅ Table '$tableName' already exists")
            return
        }

        println("🚀 Creating DynamoDB table: $tableName")

        dynamoDbClient.createTable(
            CreateTableRequest.builder()
                .tableName(tableName)
                .billingMode(BillingMode.PAY_PER_REQUEST)
                .attributeDefinitions(
                    AttributeDefinition.builder()
                        .attributeName("userId")
                        .attributeType(ScalarAttributeType.S)
                        .build()
                )
                .keySchema(
                    KeySchemaElement.builder()
                        .attributeName("userId")
                        .keyType(KeyType.HASH)
                        .build()
                )
                .build()
        )

        println("🎉 Table '$tableName' created successfully")
    }
}
