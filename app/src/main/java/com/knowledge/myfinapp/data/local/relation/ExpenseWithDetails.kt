package com.knowledge.myfinapp.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.knowledge.myfinapp.data.category.model.CategoryEntity
import com.knowledge.myfinapp.data.local.entity.ExpenseEntity
import com.knowledge.myfinapp.data.local.entity.MerchantEntity

data class ExpenseWithDetails(
    @Embedded val expense: ExpenseEntity,

    @Relation(
        parentColumn = "merchantId",
        entityColumn = "id"
    )
    val merchant: MerchantEntity?,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: CategoryEntity?
)