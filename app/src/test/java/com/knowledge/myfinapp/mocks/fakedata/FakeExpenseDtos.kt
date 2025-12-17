package com.knowledge.myfinapp.mocks.fakedata

import com.knowledge.myfinapp.data.remote.dto.ExpenseDto
import com.knowledge.myfinapp.data.remote.dto.MerchantDto
import com.knowledge.myfinapp.data.remote.dto.CategoryDto
import java.math.BigDecimal

object FakeExpenseDtos {
    val expense1Dto = ExpenseDto(
        id = "123",
        amount = BigDecimal("42.50"),
        description = "Almoço",
        merchant = MerchantDto(id = "m1", name = "Burger King", normalizedName = "BURGER KING"),
        category = CategoryDto(id = "c1", name = "Food", color = "#FF0000"),
        occurredAt = "2025-12-17T12:00:00Z",
        hash = "hash123",
        updatedAt = "2025-12-17T12:01:00Z"
    )

    val expenses =
        listOf(
            expense1Dto
        )
}

