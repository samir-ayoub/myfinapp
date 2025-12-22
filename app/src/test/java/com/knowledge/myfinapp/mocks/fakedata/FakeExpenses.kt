package com.knowledge.myfinapp.mocks.fakedata

import com.knowledge.myfinapp.data.model.Bank
import com.knowledge.myfinapp.data.model.ParsedExpenseData
import com.knowledge.myfinapp.domain.model.Expense
import com.knowledge.myfinapp.domain.model.ExpenseSource
import com.knowledge.myfinapp.domain.model.Merchant
import java.math.BigDecimal
import java.time.Instant

object FakeExpenses {
    val expense1 = Expense(
        id = "123",
        amount = BigDecimal("42.50"),
        description = "Almoço",
        merchant = Merchant(id = "m1", name = "Burger King", normalizedName = "BURGER KING"),
        category = FakeCategories.category1,
        occurredAt = Instant.parse("2025-12-17T12:00:00Z"),
        source = ExpenseSource.NOTIFICATION,
        hash = "hash123",
        updatedAt = Instant.parse("2025-12-17T12:01:00Z")
    )

    val parsedExpenseData1 = ParsedExpenseData(
        bank = Bank.CGD,
        amount = BigDecimal("12.34"),
        merchantRaw = "GLOVO BURGER KING",
        occurredAt = Instant.parse("2025-12-17T10:00:00Z"),
        isDebit = true
    )

    val expenses = listOf<Expense>(expense1)
}