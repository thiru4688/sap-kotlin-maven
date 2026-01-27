package sap.kotlin.maven.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient
import java.net.URI

@Configuration
class DynamoDbConfig(
    @Value("\${aws.region:us-east-1}")
    private val region: String,
    @Value("\${aws.dynamodb.endpoint:}")
    private val endpoint: String,
    @Value("\${aws.accessKeyId:dummy}")
    private val accessKeyId: String,
    @Value("\${aws.secretAccessKey:dummy}")
    private val secretAccessKey: String
) {

    @Bean
    fun dynamoDbClient(): DynamoDbClient {
        val builder = DynamoDbClient.builder()
            .httpClientBuilder(UrlConnectionHttpClient.builder())
            .region(Region.of(region))

        if (endpoint.isNotBlank()) {
            builder.endpointOverride(URI.create(endpoint))
            builder.credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(accessKeyId, secretAccessKey)
                )
            )
        }

        return builder.build()
    }
}
