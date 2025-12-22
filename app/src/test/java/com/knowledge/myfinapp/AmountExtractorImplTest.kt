package com.knowledge.myfinapp

import com.knowledge.myfinapp.data.notification.delegate.AmountExtractorImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class AmountExtractorImplTest {
    private lateinit var cut: AmountExtractorImpl

    @BeforeEach
    fun setup() {
        cut = AmountExtractorImpl()
    }

    @Test
    fun `extract euro amount with symbol`() {
        val text = "Purchase of € 12,34 approved"

        val amount = cut.extract(text)

        assertEquals(BigDecimal("12.34"), amount)
    }

    @Test
    fun `extract euro amount with code`() {
        val text = "Purchase of EUR12,34 confirmed"
        val amount = cut.extract(text)

        assertEquals(BigDecimal("12.34"), amount)
    }

    @Test
    fun `extract euro thousand amount`() {
        val text = "Purchase of € 1.234,56 confirmed"
        val amount = cut.extract(text)

        assertEquals(BigDecimal("1234.56"), amount)
    }

    @Test
    fun `extract euro amount with symbol after value`() {
        val text = "Purchase of 23,00 € approved"
        val amount = cut.extract(text)

        assertEquals(BigDecimal("23.00"), amount)
    }

    @Test
    fun `extract euro amount with code after value`() {
        val text = "Purchase of 23,00 EUR approved"
        val amount = cut.extract(text)

        assertEquals(BigDecimal("23.00"), amount)
    }
}