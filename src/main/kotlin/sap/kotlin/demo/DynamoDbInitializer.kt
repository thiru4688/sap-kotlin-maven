//package sap.kotlin.demo
//
//import jakarta.annotation.PostConstruct
//import org.springframework.stereotype.Component
//import software.amazon.awssdk.services.dynamodb.DynamoDbClient
//import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition
//import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement
//import software.amazon.awssdk.services.dynamodb.model.KeyType
//import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput
//import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType
//
//@Component
//class DynamoDbInitializer(
//    private val dynamoDbClient: DynamoDbClient
//) {
//
//    @PostConstruct
//    fun createTable() {
//        val tables = dynamoDbClient.listTables().tableNames()
//        if ("users" in tables) return
//
//        dynamoDbClient.createTable {
//            it.tableName("users")
//            it.keySchema(
//                KeySchemaElement.builder()
//                    .attributeName("id")
//                    .keyType(KeyType.HASH)
//                    .build()
//            )
//            it.attributeDefinitions(
//                AttributeDefinition.builder()
//                    .attributeName("id")
//                    .attributeType(ScalarAttributeType.S)
//                    .build()
//            )
//            it.provisionedThroughput(
//                ProvisionedThroughput.builder()
//                    .readCapacityUnits(5)
//                    .writeCapacityUnits(5)
//                    .build()
//            )
//        }
//    }
//}
