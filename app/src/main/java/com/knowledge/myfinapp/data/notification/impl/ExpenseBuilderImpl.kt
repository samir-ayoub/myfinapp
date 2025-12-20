package com.knowledge.myfinapp.data.notification.impl

import com.knowledge.myfinapp.data.category.heuristic.CategoryHeuristic
import com.knowledge.myfinapp.data.category.repository.CategoryRepository
import com.knowledge.myfinapp.data.notification.model.Bank
import com.knowledge.myfinapp.data.notification.model.ParsedExpenseData
import com.knowledge.myfinapp.data.notification.parser.ExpenseBuilder
import com.knowledge.myfinapp.domain.model.Category
import com.knowledge.myfinapp.domain.model.Expense
import com.knowledge.myfinapp.domain.model.ExpenseSource
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
class ExpenseBuilderImpl(
    private val categoryHeuristic: CategoryHeuristic,
    private val categoryRepository: CategoryRepository
) : ExpenseBuilder {
    override suspend fun build(data: ParsedExpenseData): Expense {
        val occurredAt = data.occurredAt
        val source = ExpenseSource.NOTIFICATION
        val category = getCategory(data.merchantRaw)
        val hash = generateHash(ExpenseHashData(
            data.bank,
            data.amount,
            data.merchantRaw,
            occurredAt
        ))

        return Expense(
            id = UUID.randomUUID().toString(),
            amount = data.amount,
            description = data.merchantRaw ?: "Unknown merchant",
            merchant = null,
            category = category,
            occurredAt = occurredAt,
            source = source,
            hash = hash,
            updatedAt = Instant.now()
        )
    }

    private suspend fun getCategory(merchantRaw: String?): Category? {
        val suggestedName = merchantRaw?.let { categoryHeuristic.categorize(it) }

        if (suggestedName.isNullOrBlank()) return null

        var currentCategory = suggestedName.let { name ->
            categoryRepository.getAllCategories().firstOrNull() { it.name == name}
        }

        if (currentCategory == null) {
            currentCategory = Category(
                id = UUID.randomUUID().toString(),
                name = suggestedName,
                color = "#FF0000"
            ).also {categoryRepository.save(it)  }
        }

        return currentCategory
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

    private fun sha256(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())

        return bytes.joinToString("") { "%02x".format(it) }
    }
}