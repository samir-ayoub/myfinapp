package com.knowledge.myfinapp.mocks.fakedata

import com.knowledge.myfinapp.domain.model.Category

object FakeCategories {
    val category1 = Category(id = "c1", name = "Food", color = "#FF0000")
    val category2 = Category(id = "c1", name = "Transport", color = "#FF0000")

    val categories = listOf<Category>(category1)
}