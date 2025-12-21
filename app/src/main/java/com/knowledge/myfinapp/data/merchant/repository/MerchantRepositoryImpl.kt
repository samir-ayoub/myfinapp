package com.knowledge.myfinapp.data.merchant.repository

import com.knowledge.myfinapp.domain.model.Merchant
import javax.inject.Inject

class MerchantRepositoryImpl @Inject constructor(): MerchantRepository {
    override suspend fun getByNormalizedName(normalizedName: String): Merchant? {
        return null
        TODO("Not yet implemented")
    }

    override suspend fun save(merchant: Merchant) {
        return
        TODO("Not yet implemented")
    }
}