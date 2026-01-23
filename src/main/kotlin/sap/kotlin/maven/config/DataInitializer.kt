package sap.kotlin.maven.config

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import sap.kotlin.maven.dynamodb.UserEntity
import sap.kotlin.maven.dynamodb.UserRepository

@Component
class DataInitializer(private val userRepository: UserRepository) {

    @PostConstruct
    fun init() {
        println("✅ UserService bean initialized")
        if (userRepository.findAll().isEmpty()) {
            println("✅ UserService bean initialized12")
            userRepository.save(UserEntity("11", "Aditya", "aditya@test.com"))
            userRepository.save(UserEntity("12", "Pravallika", "pravs@test.com"))
            userRepository.save(UserEntity("13", "Humsikha", "humsi@test.com"))
        }
    }
}


