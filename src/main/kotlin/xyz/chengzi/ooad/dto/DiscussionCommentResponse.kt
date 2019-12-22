package xyz.chengzi.ooad.dto

import xyz.chengzi.ooad.entity.DiscussionComment

class DiscussionCommentResponse(discussionComment: DiscussionComment) {
    val id = discussionComment.id
    val user = discussionComment.user?.id
    val content = discussionComment.content
    val children = discussionComment.children.map { DiscussionCommentResponse(it) }
    val timestamp = discussionComment.timestamp
}
