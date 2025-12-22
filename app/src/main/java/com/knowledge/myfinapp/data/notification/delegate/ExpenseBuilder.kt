package com.knowledge.myfinapp.data.notification.delegate

import com.knowledge.myfinapp.data.model.ParsedExpenseData
import com.knowledge.myfinapp.domain.model.Category
import com.knowledge.myfinapp.domain.model.Expense
import com.knowledge.myfinapp.domain.model.Merchant

// generates hash, sets updatedAt and target source
interface ExpenseBuilder {
    fun build(data: ParsedExpenseData, category: Category?, merchant: Merchant?): Expense
}