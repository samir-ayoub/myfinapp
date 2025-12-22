package com.knowledge.myfinapp

import com.knowledge.myfinapp.data.CategoryResolver
import com.knowledge.myfinapp.data.repository.CategoryRepository
import com.knowledge.myfinapp.data.notification.delegate.ExpenseBuilderImpl
import com.knowledge.myfinapp.domain.model.ExpenseSource
import com.knowledge.myfinapp.mocks.fakedata.FakeCategories
import com.knowledge.myfinapp.mocks.fakedata.FakeExpenses
import com.knowledge.myfinapp.mocks.fakedata.FakeMerchants
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Instant

class ExpenseBuilderImplTest {
    private lateinit var cut: ExpenseBuilderImpl
    private val mockCategoryHeuristic = mockk<CategoryResolver>()
    private val mockCategoryRepository = mockk<CategoryRepository>()

    @BeforeEach
    fun setup() {
        every { mockCategoryHeuristic.categorize(any()) } returns FakeCategories.category1.name
        coEvery { mockCategoryRepository.getAllCategories() } returns FakeCategories.categories
        coEvery { mockCategoryRepository.save(any()) } just Runs

        cut = ExpenseBuilderImpl(mockCategoryHeuristic, mockCategoryRepository)
    }

    @Test
    fun `build expense with proper data`() = runTest {
        val data = FakeExpenses.parsedExpenseData1

        val expense = cut.build(data,FakeCategories.category1, FakeMerchants.merchant1)

        assertEquals(data.amount, expense.amount)
        assertEquals("GLOVO BURGER KING", expense.description)
        assertEquals(FakeCategories.category1, expense.category)
        assertEquals(ExpenseSource.NOTIFICATION, expense.source)
        assertNotNull(expense.hash)
    }

    @Test
    fun `category is null when heuristic does not return category`() = runTest {
        every { mockCategoryHeuristic.categorize(any()) } returns null


        val data = FakeExpenses.parsedExpenseData1

        val expense = cut.build(data, FakeCategories.category1, FakeMerchants.merchant1)

        assertEquals(data.amount, expense.amount)
        assertEquals(null, expense.category)
        assertNotNull(expense.hash)
    }

    @Test
    fun `creates and save a new category`() = runTest {
        every { mockCategoryHeuristic.categorize(any()) } returns FakeCategories.category2.name

        val data = FakeExpenses.parsedExpenseData1

        val expense = cut.build(data, FakeCategories.category1, FakeMerchants.merchant1)

        assertEquals(data.amount, expense.amount)
        assertEquals(FakeCategories.category2.name, expense.category?.name)
        assertEquals(FakeCategories.category2.color, expense.category?.color)
        assertNotNull(expense.category?.id)

        coVerify(exactly = 1) {
            mockCategoryRepository.save(
                match { category ->
                    category.name == FakeCategories.category2.name &&
                            category.color == FakeCategories.category2.color
                }
            )
        }    }

    @Test
    fun `same data with time-scale less than five minutes generates same hash`() = runTest {
        val data1 = FakeExpenses.parsedExpenseData1
        val data2 = data1.copy(occurredAt = Instant.parse("2025-12-17T10:01:00Z"))
        val data3 = data1.copy(occurredAt = Instant.parse("2025-12-17T10:04:00Z"))

        val hash1 = cut.build(data1, FakeCategories.category1, FakeMerchants.merchant1).hash
        val hash2 = cut.build(data2, FakeCategories.category1, FakeMerchants.merchant1).hash
        val hash3 = cut.build(data3, FakeCategories.category1, FakeMerchants.merchant1).hash

        assertEquals(hash1, hash2)
        assertEquals(hash1, hash3)
    }

    @Test
    fun `same data with time-scale more than five minutes generates different hash`() = runTest {
        val data1 = FakeExpenses.parsedExpenseData1
        val data2 = data1.copy(occurredAt = Instant.parse("2025-12-17T10:06:00Z"))

        val hash1 = cut.build(data1, FakeCategories.category1, FakeMerchants.merchant1).hash
        val hash2 = cut.build(data2,FakeCategories.category1, FakeMerchants.merchant1).hash

        assertNotEquals(hash1, hash2)
    }
}