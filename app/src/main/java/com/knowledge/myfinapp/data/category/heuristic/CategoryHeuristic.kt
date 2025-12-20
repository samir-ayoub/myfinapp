package com.knowledge.myfinapp.data.category.heuristic

import com.knowledge.myfinapp.domain.model.Category

interface CategoryHeuristic {
    fun categorize(merchant: String): String?
}