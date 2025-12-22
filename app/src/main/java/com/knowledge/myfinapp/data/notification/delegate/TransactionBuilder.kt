package com.knowledge.myfinapp.data.notification.delegate

import com.knowledge.myfinapp.data.model.ParsedNotification
import com.knowledge.myfinapp.domain.model.Category
import com.knowledge.myfinapp.domain.model.Transaction
import com.knowledge.myfinapp.domain.model.Merchant

// generates hash, sets updatedAt and target source
interface TransactionBuilder {
    fun build(data: ParsedNotification, category: Category?, merchant: Merchant?): Transaction
}