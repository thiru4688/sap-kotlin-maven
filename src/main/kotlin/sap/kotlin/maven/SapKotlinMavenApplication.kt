package sap.kotlin.maven

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class SapKotlinMavenApplication

fun main(args: Array<String>) {
	runApplication<SapKotlinMavenApplication>(*args)
}
