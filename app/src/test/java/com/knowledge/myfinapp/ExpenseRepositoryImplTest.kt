package com.knowledge.myfinapp

import com.knowledge.myfinapp.data.remote.api.ExpenseApi
import com.knowledge.myfinapp.data.repository.ExpenseRepositoryImpl
import com.knowledge.myfinapp.mocks.fakedata.FakeExpenseDtos
import com.knowledge.myfinapp.mocks.fakedata.FakeExpenses
import com.knowledge.myfinapp.mocks.fakedata.FakeInstant
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

class ExpenseRepositoryImplTest {
    private lateinit var cut: ExpenseRepositoryImpl

    private val mockExpenseApi = mockk<ExpenseApi>()

    @BeforeEach
    fun setup() {
        cut = ExpenseRepositoryImpl(mockExpenseApi)
    }

    @Test
    fun `observeExpenses emits empty list initially`() = runTest {
        val result = cut.observeExpenses().first()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `addExpense should call expenseApi with correct DTO`() = runTest {
        coEvery { mockExpenseApi.sendExpenses(any()) } just runs

        cut.addExpense(FakeExpenses.expense1)

        coVerify(exactly = 1) {
            mockExpenseApi.sendExpenses(FakeExpenseDtos.expenses)
        }
    }

    @Test
    fun `fetchRemoteExpenses should call getExpenses and returns the result`() = runTest {
        val updatedAfter = FakeInstant.FIXED_TIME_1

        coEvery { mockExpenseApi.getExpenses(any()) } returns FakeExpenseDtos.expenses

        val result = cut.fetchRemoteExpenses(updatedAfter)

        assertEquals(1, result.size)
        assertEquals(FakeExpenseDtos.expense1Dto.id, result[0].id)
        assertEquals(FakeExpenseDtos.expense1Dto.description, result[0].description)


        coVerify(exactly = 1) {
            mockExpenseApi.getExpenses(updatedAfter.toEpochMilli())
        }
    }

    @Test
    fun `getById should call getExpenseById with id and returns the result`() = runTest {
        coEvery { mockExpenseApi.getExpenseById(any()) } returns FakeExpenseDtos.expense1Dto

        val result = cut.getById(FakeExpenses.expense1.id)

        assertEquals(FakeExpenses.expense1, FakeExpenses.expense1)

        coVerify {
            mockExpenseApi.getExpenseById(FakeExpenseDtos.expense1Dto.id)
        }
    }

    @Test
    fun `pushExpenses should call expenseApi with correct DTO`() = runTest {
        coEvery { mockExpenseApi.sendExpenses(any()) } just runs

        cut.pushExpenses(FakeExpenses.expenses)

        coVerify(exactly = 1) {
            mockExpenseApi.sendExpenses(FakeExpenseDtos.expenses)
        }
    }
}