package xyz.chengzi.ooad.dto

import xyz.chengzi.ooad.entity.Discussion

class DiscussionResponse(discussion: Discussion) {
    val id = discussion.id
    val user = discussion.user?.id
    val comment = discussion.comment
    val children = discussion.children.map { DiscussionResponse(it) }
}
