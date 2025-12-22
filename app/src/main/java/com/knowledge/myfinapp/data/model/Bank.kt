package com.knowledge.myfinapp.data.model

enum class Bank {
    MILLENNIUM,
    CGD,
    NOVO_BANCO,
    SANTANDER_PT,
    BPI,
    CTT,
    MONTEPIO,
    INVEST,
    ACTIVO_BANK,
    I_AM_GOD
}

enum class SourceType {
    PACKAGE_NAME, TEXT
}

data class BankDetection(
    val bank: Bank,
    val source: SourceType
)

