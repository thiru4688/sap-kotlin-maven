package sap.kotlin.maven.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI
/*

@Configuration
class DynamoDBConfig {

    @Bean
    fun dynamoDbClient(): DynamoDbClient =
        DynamoDbClient.builder()
            .region(Region.AP_SOUTH_1)
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build()

    @Bean
    fun enhancedClient(
        dynamoDbClient: DynamoDbClient
    ): DynamoDbEnhancedClient =
        DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build()
}
*/

@Configuration
@Profile("local")
class LocalDynamoDbConfig {

    @Bean
    fun dynamoDbClient(): DynamoDbClient =
        DynamoDbClient.builder()
            .endpointOverride(URI.create("http://localhost:8000"))
            .region(Region.AP_SOUTH_1)
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create("dummy", "dummy")
                )
            )
            .build()

    @Bean
    fun enhancedClient(
        dynamoDbClient: DynamoDbClient
    ): DynamoDbEnhancedClient =
        DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build()
}