package com.knowledge.myfinapp.data.notification.impl

import com.knowledge.myfinapp.data.notification.model.Bank
import com.knowledge.myfinapp.data.notification.model.ParsedExpenseData
import com.knowledge.myfinapp.data.notification.parser.ExpenseBuilder
import com.knowledge.myfinapp.domain.model.Category
import com.knowledge.myfinapp.domain.model.Expense
import com.knowledge.myfinapp.domain.model.ExpenseSource
import com.knowledge.myfinapp.domain.model.Merchant
import java.math.BigDecimal
import java.security.MessageDigest
import java.time.Instant
import java.util.UUID

data class ExpenseHashData(
    val bank: Bank,
    val amount: BigDecimal,
    val merchant: String?,
    val occurredAt: Instant
)
class ExpenseBuilderImpl: ExpenseBuilder {
    override fun build(
        data: ParsedExpenseData,
        category: Category?,
        merchant: Merchant?
    ): Expense {
        val hash = generateHash(ExpenseHashData(
            data.bank,
            data.amount,
            data.merchantRaw,
            data.occurredAt
        ))

        return Expense(
            id = UUID.randomUUID().toString(),
            amount = data.amount,
            description = data.merchantRaw ?: "Unknown merchant",
            merchant = merchant,
            category = category,
            occurredAt = data.occurredAt,
            source = ExpenseSource.NOTIFICATION,
            hash = hash,
            updatedAt = Instant.now()
        )
    }

    private fun generateHash(hashData: ExpenseHashData): String {
        val bankName = hashData.bank.name
        val amount = hashData.amount
        val merchant = hashData.merchant?.uppercase() ?: ""
        // Rounds timestamp to 5 minutes to allow for duplicate notifications
        val bucket = hashData.occurredAt.epochSecond / 300
        // merchant may also be included
        val input = "${bankName}-${amount}-${merchant}-${bucket}"

        return sha256(input)
    }

    private fun sha256(input: String): String =
        MessageDigest.getInstance("SHA-256")
            .digest(input.toByteArray())
            .joinToString("") { "%02x".format(it) }
}