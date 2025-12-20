//package com.knowledge.myfinapp
//
//import com.knowledge.myfinapp.data.notification.impl.ExpenseBuilderImpl
//import com.knowledge.myfinapp.domain.model.ExpenseSource
//import com.knowledge.myfinapp.mocks.fakedata.FakeExpenses
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Assertions.assertNotEquals
//import org.junit.jupiter.api.Assertions.assertNotNull
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import java.time.Instant
//
//class ExpenseBuilderImplTest {
//    private lateinit var cut: ExpenseBuilderImpl
//
//    @BeforeEach
//    fun setup() {
//        cut = ExpenseBuilderImpl()
//    }
//
//    @Test
//    fun `build expense with merchant`() {
//        val data = FakeExpenses.parsedExpenseData1
//
//        val expense = cut.build(data)
//
//        assertEquals(data.amount, expense.amount)
//        assertEquals("GLOVO BURGER KING", expense.description)
//        assertEquals(ExpenseSource.NOTIFICATION, expense.source)
//        assertNotNull(expense.hash)
//    }
//
//    @Test
//    fun `same data with time-scale less than five minutes generates same hash`() {
//        val data1 = FakeExpenses.parsedExpenseData1
//        val data2 = data1.copy(occurredAt = Instant.parse("2025-12-17T10:01:00Z"))
//        val data3 = data1.copy(occurredAt = Instant.parse("2025-12-17T10:04:00Z"))
//
//        val hash1 = cut.build(data1).hash
//        val hash2 = cut.build(data2).hash
//        val hash3 = cut.build(data3).hash
//
//        assertEquals(hash1, hash2)
//        assertEquals(hash1, hash3)
//    }
//
//    @Test
//    fun `same data with time-scale more than five minutes generates different hash`() {
//        val data1 = FakeExpenses.parsedExpenseData1
//        val data2 = data1.copy(occurredAt = Instant.parse("2025-12-17T10:06:00Z"))
//
//        val hash1 = cut.build(data1).hash
//        val hash2 = cut.build(data2).hash
//
//        assertNotEquals(hash1, hash2)
//    }
//}