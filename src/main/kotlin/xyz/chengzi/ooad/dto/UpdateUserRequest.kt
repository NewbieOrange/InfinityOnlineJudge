package xyz.chengzi.ooad.dto

import xyz.chengzi.ooad.embeddable.Gender

data class UpdateUserRequest(val displayName: String, val gender: Gender, val email: String, val biography : String)
