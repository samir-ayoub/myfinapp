package com.knowledge.myfinapp.data.notification.delegate

import com.knowledge.myfinapp.data.model.Bank
import com.knowledge.myfinapp.data.model.ParsedNotification
import com.knowledge.myfinapp.domain.model.Category
import com.knowledge.myfinapp.domain.model.Transaction
import com.knowledge.myfinapp.domain.model.Merchant
import com.knowledge.myfinapp.domain.model.TransactionSource
import java.math.BigDecimal
import java.security.MessageDigest
import java.time.Instant
import java.util.UUID

data class TransactionHashData(
    val bank: Bank,
    val amount: BigDecimal,
    val merchant: String?,
    val occurredAt: Instant
)
class TransactionBuilderImpl: TransactionBuilder {
    override fun build(
        data: ParsedNotification,
        category: Category?,
        merchant: Merchant?
    ): Transaction {
        val hash = generateHash(TransactionHashData(
            data.bank,
            data.amount,
            data.merchantRaw,
            data.occurredAt
        ))

        return Transaction(
            id = UUID.randomUUID().toString(),
            amount = data.amount,
            description = data.merchantRaw ?: "Unknown merchant",
            merchant = merchant,
            category = category,
            occurredAt = data.occurredAt,
            source = TransactionSource.NOTIFICATION,
            hash = hash,
            updatedAt = Instant.now()
        )
    }

    private fun generateHash(hashData: TransactionHashData): String {
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