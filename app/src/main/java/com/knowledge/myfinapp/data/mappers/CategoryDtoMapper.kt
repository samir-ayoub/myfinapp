package com.knowledge.myfinapp.data.mappers

import com.knowledge.myfinapp.data.remote.dto.CategoryDto
import com.knowledge.myfinapp.domain.model.Category

fun Category.toDto(): CategoryDto =
    CategoryDto(
        id = id,
        name = name,
        color = color
    )
