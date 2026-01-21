package sap.kotlin.maven

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SapKotlinMavenApplication

fun main(args: Array<String>) {
	runApplication<SapKotlinMavenApplication>(*args)
}
