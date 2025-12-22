package com.knowledge.myfinapp.data.notification.model

data class ConfidenceResult(
    val score: Double,
    val reasons: Set<ConfidenceReason>
)