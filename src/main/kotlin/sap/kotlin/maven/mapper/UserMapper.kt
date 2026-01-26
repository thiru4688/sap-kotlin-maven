//package sap.kotlin.maven.mapper
//
//import sap.kotlin.maven.entity.UserDynamoEntity
//import sap.kotlin.maven.model.UserDto
//
//fun UserDynamoEntity.toDto(): UserDto {
//    return UserDto(
//        id = this.id,
//        name = this.name,
//        email = this.email
//    )
//}
//
//fun UserDto.toEntity(): UserDynamoEntity {
//    return UserDynamoEntity(
//        id = this.id,
//        name = this.name,
//        email = this.email
//    )
//}
