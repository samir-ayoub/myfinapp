package com.knowledge.myfinapp

import com.knowledge.myfinapp.data.notification.delegate.TransactionBuilderImpl
import com.knowledge.myfinapp.domain.model.ExpenseSource
import com.knowledge.myfinapp.mocks.fakedata.FakeCategories
import com.knowledge.myfinapp.mocks.fakedata.FakeTransactions
import com.knowledge.myfinapp.mocks.fakedata.FakeMerchants
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Instant

class ExpenseBuilderImplTest {

    private lateinit var cut: TransactionBuilderImpl

    @BeforeEach
    fun setup() {
        cut = TransactionBuilderImpl()
    }

    @Test
    fun `build expense with proper data`() {
        val data = FakeTransactions.parsedNotification1

        val expense = cut.build(
            data = data,
            category = FakeCategories.category1,
            merchant = FakeMerchants.merchant1
        )

        assertEquals(data.amount, expense.amount)
        assertEquals(data.merchantRaw, expense.description)
        assertEquals(FakeCategories.category1, expense.category)
        assertEquals(ExpenseSource.NOTIFICATION, expense.source)
        assertEquals(data.occurredAt, expense.occurredAt)
        assertNotNull(expense.hash)
        assertNotNull(expense.id)
    }

    @Test
    fun `merchant description fallback when merchantRaw is null`() {
        val data = FakeTransactions.parsedNotification1.copy(merchantRaw = null)

        val expense = cut.build(
            data = data,
            category = FakeCategories.category1,
            merchant = null
        )

        assertEquals("Unknown merchant", expense.description)
    }

    @Test
    fun `same data within five minute window generates same hash`() {
        val base = FakeTransactions.parsedNotification1.copy(
            occurredAt = Instant.parse("2025-12-17T10:00:00Z")
        )

        val data1 = base
        val data2 = base.copy(occurredAt = Instant.parse("2025-12-17T10:01:00Z"))
        val data3 = base.copy(occurredAt = Instant.parse("2025-12-17T10:04:59Z"))

        val hash1 = cut.build(data1, null, null).hash
        val hash2 = cut.build(data2, null, null).hash
        val hash3 = cut.build(data3, null, null).hash

        assertEquals(hash1, hash2)
        assertEquals(hash1, hash3)
    }

    @Test
    fun `same data after five minute window generates different hash`() {
        val base = FakeTransactions.parsedNotification1.copy(
            occurredAt = Instant.parse("2025-12-17T10:00:00Z")
        )

        val data1 = base
        val data2 = base.copy(occurredAt = Instant.parse("2025-12-17T10:06:00Z"))

        val hash1 = cut.build(data1, null, null).hash
        val hash2 = cut.build(data2, null, null).hash

        assertNotEquals(hash1, hash2)
    }
}
