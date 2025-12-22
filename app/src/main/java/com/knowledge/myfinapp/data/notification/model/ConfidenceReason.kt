package com.knowledge.myfinapp.data.notification.model

enum class ConfidenceReason(
    val weight: Double,
    val message: String
) {
    BANK_BY_PACKAGE(
        weight = 0.4,
        message = "Bank detected by the package name."
    ),

    BANK_BY_TEXT(
        weight = 0.2,
        message = "Bank detected by the notification text"
    ),

    AMOUNT_DETECTED(
        weight = 0.3,
        message = "Amount identified with success"
    ),

    MERCHANT_DETECTED(
        weight = 0.2,
        message = "Merchant identified"
    ),

    CATEGORY_DETECTED(
        weight = 0.2,
        message = "Category identified"
    )
}