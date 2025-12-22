package com.knowledge.myfinapp.data.notification.delegate

interface MerchantNormalizer {
    fun normalize(text: String): String?
}