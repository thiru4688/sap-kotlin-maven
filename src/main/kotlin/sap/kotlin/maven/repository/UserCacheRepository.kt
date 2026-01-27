package sap.kotlin.maven.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import sap.kotlin.maven.cachekeys.CacheKeys
import sap.kotlin.maven.model.UserDto

@Component
class UserCacheRepository(
    private val redisTemplate: RedisTemplate<String, Any>
) {

    private val valueOps = redisTemplate.opsForValue()

    fun getAllUsers(): List<UserDto>? {
        val raw = valueOps.get(CacheKeys.USERS_ALL) as? List<*>
        return raw?.mapNotNull { obj ->
            (obj as? Map<*, *>)?.let { map ->
                UserDto(
                    id = map["id"].toString(),
                    name = map["name"].toString(),
                    email = map["email"].toString()
                )
            }
        }
    }

    fun saveAllUsers(users: List<UserDto>) {
        valueOps.set(CacheKeys.USERS_ALL, users)
    }

    fun getUserById(id: String): UserDto? {
        val raw = valueOps.get(CacheKeys.userById(id)) as? Map<*, *>
        return raw?.let {
            UserDto(
                id = it["id"].toString(),
                name = it["name"].toString(),
                email = it["email"].toString()
            )
        }
    }


    fun saveUser(user: UserDto) {
        valueOps.set(CacheKeys.userById(user.id), user)
    }

    fun evictAll() {
        redisTemplate.delete(CacheKeys.USERS_ALL)
    }

    fun evictUser(id: String) {
        redisTemplate.delete(CacheKeys.userById(id))
    }
}