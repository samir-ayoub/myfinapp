package com.knowledge.myfinapp.data.notification.listener

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.knowledge.myfinapp.data.notification.model.RawNotification
import com.knowledge.myfinapp.data.notification.parser.ExpenseBuilder
import com.knowledge.myfinapp.data.notification.parser.NotificationParser
import com.knowledge.myfinapp.data.notification.parser.ParseResult
import com.knowledge.myfinapp.domain.model.Expense
import com.knowledge.myfinapp.domain.usecase.AddExpenseUseCase
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.Instant

@AndroidEntryPoint
class BankNotificationListener : NotificationListenerService() {

    @Inject lateinit var notificationParser: NotificationParser
    @Inject lateinit var expenseBuilder: ExpenseBuilder
    @Inject lateinit var addExpenseUseCase: AddExpenseUseCase

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        Timber.i("New notification detected. Parsing...")
        sbn ?: return

        val rawNotification = sbn.toRawNotification() ?: return

        when(val result = notificationParser.parse(rawNotification)) {
            is ParseResult.Failure -> Timber.e("parse failed")
            is ParseResult.Ignored -> Timber.i("parse ignored due to: ${result.reason}")
            is ParseResult.Success -> {
                val expense = expenseBuilder.build(result.data)
                Timber.i("registering new expense: $expense")
                addExpense(expense)
            }
        }
    }

    private fun addExpense(expense: Expense) {
        CoroutineScope(Dispatchers.IO).launch {
            addExpenseUseCase(expense)
        }
    }

    fun StatusBarNotification.toRawNotification(): RawNotification? {
        val text = notification.extras.getCharSequence(Notification.EXTRA_TEXT)?.toString() ?: return null
        val title = notification.extras.getCharSequence(Notification.EXTRA_TITLE)?.toString()
        val postedAt = Instant.ofEpochMilli(postTime)
        val rawNotification = RawNotification(
            packageName = packageName,
            title = title,
            text = text,
            postedAt = postedAt
        )

        Timber.i("mapped raw notification: $rawNotification")

        return rawNotification
    }
}