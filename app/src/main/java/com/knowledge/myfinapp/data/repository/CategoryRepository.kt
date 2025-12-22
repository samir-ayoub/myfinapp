package com.knowledge.myfinapp.data.repository

import com.knowledge.myfinapp.domain.model.Category
import com.knowledge.myfinapp.domain.model.Merchant

interface CategoryRepository {
    suspend fun getCategoryByMerchant(merchant: Merchant): Category?
    suspend fun getAllCategories(): List<Category>
    suspend fun save(category: Category)
}