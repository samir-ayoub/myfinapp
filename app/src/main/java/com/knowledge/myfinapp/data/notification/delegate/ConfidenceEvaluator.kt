package com.knowledge.myfinapp.data.notification.delegate

import com.knowledge.myfinapp.data.model.ParsingEvidence
import com.knowledge.myfinapp.data.notification.model.ConfidenceReason
import com.knowledge.myfinapp.data.notification.model.ConfidenceResult
import javax.inject.Inject

class ConfidenceEvaluator @Inject constructor() {
        fun evaluate(context: ParsingEvidence): ConfidenceResult {
        val reasons = mutableSetOf<ConfidenceReason>()

        if (context.bankDetectedByPackageName) {
            reasons.add(ConfidenceReason.BANK_BY_PACKAGE)
        } else {
            reasons.add(ConfidenceReason.BANK_BY_TEXT)
        }

        if (context.amountDetected) {
            reasons.add(ConfidenceReason.AMOUNT_DETECTED)
        }

        if (context.merchantDetected) {
            reasons.add(ConfidenceReason.MERCHANT_DETECTED)
        }

        val score = reasons.sumOf { it.weight }.coerceAtMost(1.0)


        return ConfidenceResult(score, reasons)
    }
}