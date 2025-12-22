package com.knowledge.myfinapp.ui.transaction

sealed class TransactionEditorMode {
    object CreateExpense: TransactionEditorMode()
    object CreateIncome: TransactionEditorMode()
    data class Edit(val transactionId: String) : TransactionEditorMode()
}