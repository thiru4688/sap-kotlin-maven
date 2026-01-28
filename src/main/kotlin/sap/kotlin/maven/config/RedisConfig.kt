package sap.kotlin.maven.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisClusterConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig(
    @Value("\${redis.cluster.enabled:false}") private val clusterEnabled: Boolean,
    @Value("\${redis.cluster.nodes:}") private val clusterNodes: String,
    @Value("\${redis.host:localhost}") private val redisHost: String,
    @Value("\${redis.port:6379}") private val redisPort: Int,
    @Value("\${redis.password:}") private val redisPassword: String,
    @Value("\${redis.ssl:false}") private val redisSsl: Boolean
) {

    private val log = LoggerFactory.getLogger(RedisConfig::class.java)

    @Bean
    fun lettuceClientConfiguration(): LettuceClientConfiguration {
        val builder = LettuceClientConfiguration.builder()
        if (redisSsl) {
            log.info("Enabling SSL/TLS for Redis connections")
            builder.useSsl()
        }
        return builder.build()
    }

    @Bean
    fun redisConnectionFactory(lettuceConfig: LettuceClientConfiguration): RedisConnectionFactory {
        return try {
            if (clusterEnabled) {
                val nodes = clusterNodes.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                if (nodes.isEmpty()) {
                    log.warn("redis.cluster.enabled=true but no redis.cluster.nodes supplied; falling back to standalone config")
                    createStandaloneFactory(lettuceConfig)
                } else {
                    val cfg = RedisClusterConfiguration(nodes)
                    if (redisPassword.isNotBlank()) cfg.setPassword(RedisPassword.of(redisPassword))
                    LettuceConnectionFactory(cfg, lettuceConfig)
                }
            } else {
                createStandaloneFactory(lettuceConfig)
            }
        } catch (ex: Exception) {
            log.error("Error creating RedisConnectionFactory, rethrowing", ex)
            throw ex
        }
    }

    private fun createStandaloneFactory(lettuceConfig: LettuceClientConfiguration): LettuceConnectionFactory {
        val cfg = RedisStandaloneConfiguration(redisHost, redisPort)
        if (redisPassword.isNotBlank()) cfg.password = RedisPassword.of(redisPassword)
        return LettuceConnectionFactory(cfg, lettuceConfig)
    }

    @Bean
    fun redisTemplate(factory: RedisConnectionFactory): RedisTemplate<String, String> {
        val template = RedisTemplate<String, String>()
        template.setConnectionFactory(factory)
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = StringRedisSerializer()
        template.afterPropertiesSet()
        return template
    }

    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapper().registerKotlinModule()
}
