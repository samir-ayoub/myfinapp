package com.knowledge.myfinapp.data.mappers.local

import com.knowledge.myfinapp.data.local.entity.CategoryEntity
import com.knowledge.myfinapp.domain.model.Category

fun CategoryEntity.toDomain(): Category =
    Category(
        id = id,
        name = name,
        color = color
    )

fun Category.toEntity(): CategoryEntity =
    CategoryEntity(
        id = id,
        name = name,
        color = color
    )