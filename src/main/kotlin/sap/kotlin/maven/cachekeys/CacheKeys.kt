package sap.kotlin.maven.cachekeys

object CacheKeys {
    const val USERS_ALL = "users:all"
    fun userById(id: String) = "users:$id"
}
