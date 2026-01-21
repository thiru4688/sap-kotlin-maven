package sap.kotlin.maven.repo

import org.springframework.data.jpa.repository.JpaRepository
import sap.kotlin.maven.model.UserDto
import sap.kotlin.maven.model.UserEntity


interface UserRepository : JpaRepository<UserEntity, Long> {
}
