package com.knowledge.myfinapp.data.notification.parser

interface MerchantNormalizer {
    fun normalize(text: String): String?
}