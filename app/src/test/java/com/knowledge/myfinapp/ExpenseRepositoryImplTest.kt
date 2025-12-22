package com.knowledge.myfinapp

import com.knowledge.myfinapp.data.remote.api.TransactionApi
import com.knowledge.myfinapp.data.repository.TransactionRepositoryImpl
import com.knowledge.myfinapp.mocks.fakedata.FakeTransactionDtos
import com.knowledge.myfinapp.mocks.fakedata.FakeTransactions
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
    private lateinit var cut: TransactionRepositoryImpl

    private val mockExpenseApi = mockk<TransactionApi>()

    @BeforeEach
    fun setup() {
        cut = TransactionRepositoryImpl(mockExpenseApi)
    }

    @Test
    fun `observeExpenses emits empty list initially`() = runTest {
        val result = cut.observeTransactions().first()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `addExpense should call expenseApi with correct DTO`() = runTest {
        coEvery { mockExpenseApi.sendTransactions(any()) } just runs

        cut.addTransaction(FakeTransactions.transaction1)

        coVerify(exactly = 1) {
            mockExpenseApi.sendTransactions(FakeTransactionDtos.transactions)
        }
    }

    @Test
    fun `fetchRemoteExpenses should call getExpenses and returns the result`() = runTest {
        val updatedAfter = FakeInstant.FIXED_TIME_1

        coEvery { mockExpenseApi.getTransactions(any()) } returns FakeTransactionDtos.transactions

        val result = cut.fetchRemoteTransactions(updatedAfter)

        assertEquals(1, result.size)
        assertEquals(FakeTransactionDtos.transaction1Dto.id, result[0].id)
        assertEquals(FakeTransactionDtos.transaction1Dto.description, result[0].description)


        coVerify(exactly = 1) {
            mockExpenseApi.getTransactions(updatedAfter.toEpochMilli())
        }
    }

    @Test
    fun `getById should call getExpenseById with id and returns the result`() = runTest {
        coEvery { mockExpenseApi.getTransactionById(any()) } returns FakeTransactionDtos.transaction1Dto

        val result = cut.getById(FakeTransactions.transaction1.id)

        assertEquals(FakeTransactions.transaction1, FakeTransactions.transaction1)

        coVerify {
            mockExpenseApi.getTransactionById(FakeTransactionDtos.transaction1Dto.id)
        }
    }

    @Test
    fun `pushExpenses should call expenseApi with correct DTO`() = runTest {
        coEvery { mockExpenseApi.sendTransactions(any()) } just runs

        cut.pushTransactions(FakeTransactions.expenses)

        coVerify(exactly = 1) {
            mockExpenseApi.sendTransactions(FakeTransactionDtos.transactions)
        }
    }
}