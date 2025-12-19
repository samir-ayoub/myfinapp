package com.knowledge.myfinapp.data.notification.impl

import com.knowledge.myfinapp.data.notification.model.Bank
import com.knowledge.myfinapp.data.notification.parser.BankDetector
import timber.log.Timber

// to analyse the list of packages in the environment
//adb shell pm list packages | grep <bank>
class BankDetectorImpl: BankDetector {
    private val packageMap: Map<String, Bank> = mapOf(
        "pt.millenniumbcp" to Bank.MILLENNIUM,
        "pt.cgd.mobile" to Bank.CGD,
        "pt.novobanco.app" to Bank.NOVO_BANCO,
        "pt.santander.totta" to Bank.SANTANDER_PT,
        "pt.bpi.app" to Bank.BPI,
        "pt.banco.ctt" to Bank.CTT,
        "pt.montepio" to Bank.MONTEPIO,
        "pt.bancoinvest" to Bank.INVEST,
        "wit.android.bcpBankingApp.activoBank" to Bank.ACTIVO_BANK,
        "com.knowledge.notificationsender" to Bank.I_AM_GOD
        )

    override fun detect(packageName: String, text: String): Bank? {
        packageMap[packageName]?.let { return it }

        val normalizedText = text.lowercase()
        Timber.i("mapping to banks from text $normalizedText")

        for ((bank, keywords) in KEYWORDS) {
            if (keywords.any { normalizedText.contains(it) }) return bank
        }

        return null
    }

    companion object {
        var KEYWORDS: Map<Bank, List<String>> = mapOf(
            Bank.ACTIVO_BANK to listOf("activobank", "activo bank"),
            Bank.MILLENNIUM to listOf("millennium", "bcp"),
            Bank.CGD to listOf("cgd", "caixa geral"),
            Bank.NOVO_BANCO to listOf("novo banco", "novobanco"),
            Bank.SANTANDER_PT to listOf("santander", "totta"),
            Bank.BPI to listOf("bpi", "banco bpi"),
            Bank.CTT to listOf("ctt", "banco ctt"),
            Bank.MONTEPIO to listOf("montepio"),
            Bank.INVEST to listOf("invest", "banco invest"),
            Bank.I_AM_GOD to listOf("notificationsender")
        )
    }
}