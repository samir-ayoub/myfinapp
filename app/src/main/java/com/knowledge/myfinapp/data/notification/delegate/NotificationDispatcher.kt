package com.knowledge.myfinapp.data.notification.delegate

import com.knowledge.myfinapp.data.model.RawNotification
import com.knowledge.myfinapp.data.notification.delegate.NotificationParser
import com.knowledge.myfinapp.data.notification.parser.ParseResult
import com.knowledge.myfinapp.domain.usecase.AddExpenseUseCase
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationDispatcher @Inject constructor(
    private val notificationParser: NotificationParser,
    private val expenseBuilder: ExpenseBuilder,
    private val addExpenseUseCase: AddExpenseUseCase
) {

    suspend fun dispatch(rawNotification: RawNotification) {
        when (val result = notificationParser.parse(rawNotification)) {
            is ParseResult.Failure -> Timber.Forest.e("Parse failed")
            is ParseResult.Ignored -> Timber.Forest.i("Parse ignored: ${result.reason}")
            is ParseResult.Success -> addExpenseUseCase(result.data)
        }
    }
}