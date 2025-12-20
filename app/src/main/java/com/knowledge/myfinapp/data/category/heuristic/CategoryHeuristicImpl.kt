package com.knowledge.myfinapp.data.category.heuristic

import javax.inject.Inject

class CategoryHeuristicImpl @Inject constructor() : CategoryHeuristic {
    private val keywordsToCategory = mapOf<String, String>(
        "IFOOD" to "Food",
        "UBER" to "Transport",
        "AMAZON" to "Shopping",
        "PIZZARIA" to "Food"
    )

    override fun categorize(merchant: String): String? {
        val upperMerchant = merchant.uppercase()
        return keywordsToCategory.entries
            .firstOrNull() { upperMerchant.contains(it.key) }
            ?.value
        }
    }
