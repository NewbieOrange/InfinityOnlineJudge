package xyz.chengzi.ooad.dto

import xyz.chengzi.ooad.embeddable.Visibility
import java.util.*

data class CreateContestRequest(val title: String, val description: String, val startDate: Date, val endDate: Date, val visibility: Visibility, val mode: String)
