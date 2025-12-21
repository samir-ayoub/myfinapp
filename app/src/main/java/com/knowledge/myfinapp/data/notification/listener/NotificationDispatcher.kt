package com.knowledge.myfinapp.data.notification.listener

import com.knowledge.myfinapp.data.notification.model.RawNotification
import com.knowledge.myfinapp.data.notification.parser.ExpenseBuilder
import com.knowledge.myfinapp.data.notification.parser.NotificationParser
import com.knowledge.myfinapp.data.notification.parser.ParseResult
import com.knowledge.myfinapp.domain.usecase.AddExpenseUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            is ParseResult.Failure -> Timber.e("Parse failed")
            is ParseResult.Ignored -> Timber.i("Parse ignored: ${result.reason}")
            is ParseResult.Success -> addExpenseUseCase(result.data)
        }
    }
//
//    private fun addExpense(expense: com.knowledge.myfinapp.domain.model.Expense) {
//        CoroutineScope(Dispatchers.IO).launch {
//            addExpenseUseCase(expense)
//        }
//    }
}
