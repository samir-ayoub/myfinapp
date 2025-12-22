package com.knowledge.myfinapp

import com.knowledge.myfinapp.data.CategoryResolver
import com.knowledge.myfinapp.data.repository.CategoryRepository
import com.knowledge.myfinapp.domain.model.Category
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CategoryResolverTest {

    private lateinit var cut: CategoryResolver
    private val repository = mockk<CategoryRepository>()

    @BeforeEach
    fun setup() {
        cut = CategoryResolver(repository)
    }

    @Test
    fun `returns null when merchantRaw is null`() = runTest {
        val result = cut.resolve(null)

        assertNull(result)
        coVerify { repository wasNot Called }
    }

    @Test
    fun `returns null when no keyword matches`() = runTest {
        coEvery { repository.getAllCategories() } returns emptyList()

        val result = cut.resolve("UNKNOWN SHOP")

        assertNull(result)
    }

    @Test
    fun `returns existing category when found`() = runTest {
        val existing = Category(
            id = "1",
            name = "Food",
            color = "#00FF00"
        )

        coEvery { repository.getAllCategories() } returns listOf(existing)

        val result = cut.resolve("BURGER KING")

        assertNotNull(result)
        assertEquals(existing.id, result?.id)
        assertEquals(existing.name, result?.name)

        coVerify(exactly = 0) {
            repository.save(any())
        }
    }

    @Test
    fun `creates and saves category when not found`() = runTest {
        coEvery { repository.getAllCategories() } returns emptyList()
        coEvery { repository.save(any()) } just Runs

        val result = cut.resolve("BURGER KING")

        assertNotNull(result)
        assertEquals("Food", result?.name)
        assertEquals("#FF0000", result?.color)
        assertNotNull(result?.id)

        coVerify(exactly = 1) {
            repository.save(
                match {
                    it.name == "Food" && it.color == "#FF0000"
                }
            )
        }
    }
}
