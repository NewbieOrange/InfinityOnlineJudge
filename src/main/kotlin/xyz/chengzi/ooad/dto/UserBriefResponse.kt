package xyz.chengzi.ooad.dto

import xyz.chengzi.ooad.entity.User

class UserBriefResponse(user : User) {
    val id = user.id
    val displayName = user.displayName ?: user.username
}
