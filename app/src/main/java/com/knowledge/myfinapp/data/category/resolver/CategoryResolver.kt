package com.knowledge.myfinapp.data.category.resolver

import com.knowledge.myfinapp.data.category.repository.CategoryRepository
import com.knowledge.myfinapp.domain.model.Category
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

class CategoryResolver @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend fun resolve(merchantRaw: String?): Category? {
        val categoryName = merchantRaw
            ?.let(::resolveCategoryName)
            ?.also { Timber.i("Potential category name: $it") }
            ?: return null

        return findOrCreateCategory(categoryName)
    }

    private fun resolveCategoryName(merchant: String): String? {
        val upper = merchant.uppercase()
        return CategoryRules.keywordToCategory.entries
            .firstOrNull { upper.contains(it.key) }
            ?.value
    }

    private suspend fun findOrCreateCategory(name: String): Category {
        return findCategory(name)
            ?: createCategory(name).also {
                Timber.i("Detected new category: $name")
            }
    }

    private suspend fun findCategory(name: String): Category? {
        return categoryRepository
            .getAllCategories()
            .firstOrNull { it.name == name }
    }

    private suspend fun createCategory(name: String): Category {
        return Category(
            id = UUID.randomUUID().toString(),
            name = name,
            color = "#FF0000"
        ).also {
            categoryRepository.save(it)
        }
    }
}
