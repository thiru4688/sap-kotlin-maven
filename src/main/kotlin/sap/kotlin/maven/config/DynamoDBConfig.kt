package sap.kotlin.maven.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import org.springframework.beans.factory.annotation.Value

@Configuration
class DynamoDBConfig {

    @Value("\${aws.region}")
    lateinit var region: String

    @Value("\${aws.accessKey}")
    lateinit var accessKey: String

    @Value("\${aws.secretKey}")
    lateinit var secretKey: String

    @Bean
    fun dynamoDbClient(): DynamoDbClient {

        val credentials = AwsBasicCredentials.create(accessKey, secretKey)

        return DynamoDbClient.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build()
    }

    @Bean
    fun dynamoDbEnhancedClient(dynamoDbClient: DynamoDbClient): DynamoDbEnhancedClient {

        return DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build()
    }
}
