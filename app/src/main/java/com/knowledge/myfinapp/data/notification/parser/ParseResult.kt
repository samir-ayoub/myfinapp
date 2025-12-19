package com.knowledge.myfinapp.data.notification.parser

import com.knowledge.myfinapp.data.notification.model.ParsedExpenseData

sealed class ParseResult {
    data class Success(val data: ParsedExpenseData) : ParseResult()
    data class Ignored(val reason: IgnoredStatus): ParseResult()
    data class Failure(val error: ParseError) : ParseResult()
}

sealed class ParseError {
    data object AmountNotFound : ParseError()
    data class Unexpected(val throwable: Throwable) : ParseError()
}

enum class IgnoredStatus {
    BANK_NOT_DETECTED,
    NOT_FINANCIAL_NOTIFICATION
}