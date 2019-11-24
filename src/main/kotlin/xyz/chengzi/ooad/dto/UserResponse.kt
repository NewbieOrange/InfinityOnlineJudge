package xyz.chengzi.ooad.dto

import xyz.chengzi.ooad.entity.User

class UserResponse(user: User) {
    val id = user.id
    val username = user.username
    val displayName = user.displayName
    val gender = user.gender
    val email = user.email
    val biography = user.biography
    val avatar = user.avatarId
}
