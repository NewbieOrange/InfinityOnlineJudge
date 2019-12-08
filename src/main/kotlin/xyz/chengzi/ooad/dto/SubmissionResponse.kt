package xyz.chengzi.ooad.dto

import xyz.chengzi.ooad.entity.Submission

class SubmissionResponse(submission: Submission) {
    val id = submission.id
    val problem = submission.problem.id
    val user = submission.user.id
    val status = submission.status
    val cases = submission.cases
    val language = submission.language
    val codeLength = submission.codeLength
    val timestamp = submission.timestamp
}
