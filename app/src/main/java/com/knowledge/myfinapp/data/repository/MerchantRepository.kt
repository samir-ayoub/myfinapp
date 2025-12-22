package com.knowledge.myfinapp.data.repository

import com.knowledge.myfinapp.domain.model.Merchant

interface MerchantRepository {
    suspend fun getByNormalizedName(normalizedName: String): Merchant?
    suspend fun save(merchant: Merchant)
}