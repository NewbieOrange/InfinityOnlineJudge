package xyz.chengzi.ooad.dto

data class JudgeRequestMessage(val id: Int, val pid: Int, val language: String, val timeLimit: Int, val memoryLimit: Int, val special: Boolean)
