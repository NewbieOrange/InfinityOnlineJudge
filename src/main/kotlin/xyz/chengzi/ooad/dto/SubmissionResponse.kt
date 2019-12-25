package xyz.chengzi.ooad.dto

import xyz.chengzi.ooad.entity.Submission

class SubmissionResponse(submission: Submission) {
    val id = submission.id
    val problem = submission.problem.id
    val user = UserBriefResponse(submission.user)
    val status = submission.status
    val totalTime = submission.timeUsage
    val peakMemory = submission.memoryUsage
    val cases = submission.cases
    val language = submission.language
    val codeLength = submission.codeLength
    val timestamp = submission.timestamp
}
