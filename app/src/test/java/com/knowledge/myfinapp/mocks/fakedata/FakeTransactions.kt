package com.knowledge.myfinapp.mocks.fakedata

import com.knowledge.myfinapp.data.model.Bank
import com.knowledge.myfinapp.data.model.ParsedNotification
import com.knowledge.myfinapp.data.model.ParsingEvidence
import com.knowledge.myfinapp.data.model.SourceType
import com.knowledge.myfinapp.domain.model.Transaction
import com.knowledge.myfinapp.domain.model.Merchant
import com.knowledge.myfinapp.domain.model.TransactionSource
import com.knowledge.myfinapp.domain.model.TransactionType
import java.math.BigDecimal
import java.time.Instant

object FakeTransactions {
    val transaction1 = Transaction(
        id = "123",
        amount = BigDecimal("42.50"),
        description = "Almoço",
        merchant = Merchant(id = "m1", name = "Burger King", normalizedName = "BURGER KING"),
        category = FakeCategories.category1,
        occurredAt = Instant.parse("2025-12-17T12:00:00Z"),
        source = TransactionSource.NOTIFICATION,
        hash = "hash123",
        updatedAt = Instant.parse("2025-12-17T12:01:00Z")
    )

    val parsedNotification1 = ParsedNotification(
        bank = Bank.CGD,
        amount = BigDecimal("12.34"),
        merchantRaw = "GLOVO BURGER KING",
        occurredAt = Instant.parse("2025-12-17T10:00:00Z"),
        evidences = FakeParsingEvidences.evidences1,
        type = TransactionType.EXPENSE
    )

    val expenses = listOf<Transaction>(transaction1)
}

object FakeParsingEvidences {
    val evidences1 = ParsingEvidence(
        bankDetectedByPackageName = true,
        amountDetected = true,
        merchantDetected = true
    )
}