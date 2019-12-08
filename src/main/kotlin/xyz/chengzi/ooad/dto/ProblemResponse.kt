package xyz.chengzi.ooad.dto

import xyz.chengzi.ooad.entity.Problem

class ProblemResponse(problem: Problem) {
    val id = problem.id
    val title = problem.title
    val description = problem.description
    val descriptionHtml = problem.descriptionHtml
    val type = problem.type
    val special = problem.isSpecial
    val timeLimit = problem.timeLimit
    val memoryLimit = problem.memoryLimit
    val acceptedAmount = problem.acceptedAmount
    val submissionAmount = problem.submissionAmount
}
