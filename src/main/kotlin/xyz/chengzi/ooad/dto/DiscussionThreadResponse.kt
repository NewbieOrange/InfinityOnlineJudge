package xyz.chengzi.ooad.dto

import xyz.chengzi.ooad.entity.DiscussionThread

class DiscussionThreadResponse(discussionThread: DiscussionThread) {
    val id = discussionThread.id
    val user = discussionThread.user?.id
    val title = discussionThread.title
    val content = discussionThread.content
    val children = discussionThread.children.map { DiscussionCommentResponse(it) }
    val timestamp = discussionThread.timestamp
}
