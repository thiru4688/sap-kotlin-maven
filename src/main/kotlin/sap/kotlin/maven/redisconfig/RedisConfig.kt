package sap.kotlin.maven.redisconfig

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
// IMPORTANT: Use the 'tools.jackson' imports for Spring Boot 4 compatibility
import tools.jackson.databind.ObjectMapper
import tools.jackson.databind.json.JsonMapper
import tools.jackson.module.kotlin.KotlinModule

@Configuration
class RedisConfig {

    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {

        // Use the new Jackson 3 ObjectMapper
        val kotlinModule = KotlinModule.Builder().build()

        // Use JsonMapper builder to create the ObjectMapper
        val objectMapper: ObjectMapper = JsonMapper.builder()
            .addModule(kotlinModule)
            .build()

        val serializer = GenericJacksonJsonRedisSerializer(objectMapper)

        return RedisTemplate<String, Any>().apply {
            setConnectionFactory(connectionFactory)
            keySerializer = StringRedisSerializer()
            valueSerializer = serializer
            hashKeySerializer = StringRedisSerializer()
            hashValueSerializer = serializer
            afterPropertiesSet()
        }
    }
}