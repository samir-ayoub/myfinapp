package com.knowledge.myfinapp.data.notification.delegate

import com.knowledge.myfinapp.data.model.RawNotification
import com.knowledge.myfinapp.data.notification.parser.ParseResult
import com.knowledge.myfinapp.domain.usecase.AddExpenseUseCase
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationDispatcher @Inject constructor(
    private val notificationParser: NotificationParser,
    private val addExpenseUseCase: AddExpenseUseCase,
    private val confidenceEvaluator: ConfidenceEvaluator

) {

    suspend fun dispatch(rawNotification: RawNotification) {
        when (val result = notificationParser.parse(rawNotification)) {
            is ParseResult.Failure -> Timber.Forest.e("Parse failed")
            is ParseResult.Ignored -> Timber.Forest.i("Parse ignored: ${result.reason}")
            is ParseResult.Success -> {
                val confidence = confidenceEvaluator.evaluate(result.data.evidences)
                if (confidence.score < 0.6) {
                    Timber.w("Low confidence: $confidence")
                }

                addExpenseUseCase(result.data)
            }
        }
    }
}