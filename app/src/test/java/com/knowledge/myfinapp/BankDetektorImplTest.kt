package com.knowledge.myfinapp

import com.knowledge.myfinapp.data.notification.delegate.BankDetectorImpl
import com.knowledge.myfinapp.data.model.Bank
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class BankDetektorImplTest {


    private lateinit var cut: BankDetectorImpl

    @BeforeEach
    fun setup() {
        cut = BankDetectorImpl()
    }

    @Test
    fun `returns null for unknown package and text`() {
        val result = cut.detect("com.unknown", "Mensagem genérica")
        assertNull(result)
    }

    @Test
    fun `returns null for unmapped package with unrelated text`() {
        val result = cut.detect("com.fakebank.app", "Compra aprovada")
        assertNull(result)
    }

    @ParameterizedTest
    @MethodSource("packageData")
    fun `detect bank by official package`(packageName: String, expectedBank: Bank) {
        val result = cut.detect(packageName, "Mensagem qualquer")
        assertEquals(expectedBank, result)
    }

    @ParameterizedTest
    @MethodSource("keywordData")
    fun `detect bank by keyword fallback`(text: String, expectedBank: Bank) {
        val result = cut.detect("unknown.pkg", text)
        assertEquals(expectedBank, result)
    }

    companion object {
        @JvmStatic
        fun packageData() = listOf(
            Arguments.of("pt.millenniumbcp", Bank.MILLENNIUM),
            Arguments.of("pt.cgd.mobile", Bank.CGD),
             Arguments.of("pt.novobanco.app", Bank.NOVO_BANCO),
             Arguments.of("pt.santander.totta", Bank.SANTANDER_PT),
             Arguments.of("pt.bpi.app", Bank.BPI),
             Arguments.of("pt.banco.ctt", Bank.CTT),
             Arguments.of("pt.montepio", Bank.MONTEPIO),
             Arguments.of("wit.android.bcpBankingApp.activoBank", Bank.ACTIVO_BANK)
        )

        @JvmStatic
        fun keywordData() = listOf(
             Arguments.of("Compra aprovada Millennium BCP", Bank.MILLENNIUM),
             Arguments.of("Transferência recebida CGD", Bank.CGD),
             Arguments.of("Pagamento efetuado pelo Novo Banco", Bank.NOVO_BANCO),
             Arguments.of("Compra aprovada no Santander Totta", Bank.SANTANDER_PT),
             Arguments.of("Compra aprovada pelo ActivoBank", Bank.ACTIVO_BANK)
        )
    }
}