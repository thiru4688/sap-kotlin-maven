package sap.kotlin.maven.dbconfig 
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

@Configuration
class DynamoDbConfig {
    @Bean
    fun dynamoDbClient(): DynamoDbClient {
        return DynamoDbClient.builder()
            .region(Region.of("ap-southeast-2"))
            .credentialsProvider(
                StaticCredentialsProvider.create(

                    AwsBasicCredentials.create("key", "secret")

                )
            )
            .build()
    }

    @Bean
    fun dynamoDbEnhancedClient(
        dynamoDbClient: DynamoDbClient
    ): DynamoDbEnhancedClient =
        DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build()

}