package com.knowledge.myfinapp.data

import com.knowledge.myfinapp.data.repository.MerchantRepository
import com.knowledge.myfinapp.domain.model.Merchant
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

class MerchantResolver @Inject constructor(
    private val merchantRepository: MerchantRepository
) {
    suspend fun resolve(merchantRaw: String?): Merchant? {
        val normalized = merchantRaw
            ?.let (::normalize)
            ?.also { Timber.Forest.i("Potential merchant name: $it") }
            ?: return null

        return findOrCreateMerchant(merchantRaw, normalized)
    }

    private fun normalize(raw: String): String {
        return raw
            .uppercase()
            .replace(Regex("[^A-Z0-9 ]"), "")
            .replace(Regex("\\s+"), " ")
            .trim()
    }

    private suspend fun findOrCreateMerchant(
        rawName: String,
        normalizedName: String
    ): Merchant {
        return findMerchant(normalizedName)
            ?: createMerchant(rawName, normalizedName).also {
                Timber.Forest.i("Detected new merchant: $normalizedName")

            }
    }

    private suspend fun findMerchant(normalizedName: String): Merchant? =
        merchantRepository.getByNormalizedName(normalizedName)
    private suspend fun createMerchant(
        rawName: String,
        normalizedName: String
    ) = Merchant(
        id = UUID.randomUUID().toString(),
        name = rawName,
        normalizedName = normalizedName
    ).also {
        merchantRepository.save(it)
    }

}