package com.knowledge.myfinapp.data.category.repository

import com.knowledge.myfinapp.domain.model.Category
import com.knowledge.myfinapp.domain.model.Merchant
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor() : CategoryRepository {
    override suspend fun getAllCategories(): List<Category> {
        return emptyList()
        TODO("Not yet implemented")
    }

    override suspend fun getCategoryByMerchant(merchant: Merchant): Category? {
        return null
        TODO("Not yet implemented")
    }

    override suspend fun save(category: Category) {
        return
        TODO("Not yet implemented")
    }
}