package sap.kotlin.maven.dbconfig

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class CacheService(
    private val redisTemplate: RedisTemplate<String, Any>
) {

    fun save(key: String, value: String) {
        redisTemplate.opsForValue().set(key, value)
    }

    fun get(key: String): String? =
        redisTemplate.opsForValue().get(key) as? String
}
