package com.knowledge.myfinapp

import com.knowledge.myfinapp.data.notification.impl.MerchantNormalizerImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MerchantNormalizerImplTest {
    private lateinit var cut: MerchantNormalizerImpl

    @BeforeEach
    fun setup() {
        cut = MerchantNormalizerImpl()
    }

    @Test
    fun `normalize ifood burger king BR`() {
        val text = "Compra aprovada IFOOD*BURGER KING #123 R$ 45,90"
        val merchant = cut.normalize(text)

        assertEquals("IFOOD BURGER KING", merchant)
    }

    @Test
    fun `normalize amazon BR`() {
        val text = "Você fez uma compra de R$ 32,00 em Amazon Marketplace"
        val merchant = cut.normalize(text)

        assertEquals("AMAZON MARKETPLACE", merchant)
    }

    @Test
    fun `normalize merchant euro symbol`() {
        val text = "Compra aprovada € 12,34 IFOOD*BURGER KING #123"
        val merchant = cut.normalize(text)

        assertEquals("IFOOD BURGER KING", merchant)
    }

    @Test
    fun `normalize merchant euro code`() {
        val text = "Transação EUR 45,67 AMAZON MARKETPLACE realizada"
        val merchant = cut.normalize(text)

        assertEquals("AMAZON MARKETPLACE", merchant)
    }

    @Test
    fun `normalize merchant euro with noise`() {
        val text = "Pagamento realizado EUR 1.234,56 UBER TRIP #9987"
        val merchant = cut.normalize(text)

        assertEquals("UBER TRIP", merchant)
    }

    @Test
    fun `return null when euro text has no merchant`() {
        val text = "Saldo atualizado EUR 1.000,00"
        val merchant = cut.normalize(text)

        assertNull(merchant)
    }

    @Test
    fun `normalize merchant euro with line breaks`() {
        val text = "Compra\nEUR 23,45\nAMAZON MARKETPLACE"
        val merchant = cut.normalize(text)

        assertEquals("AMAZON MARKETPLACE", merchant)
    }
}