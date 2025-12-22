package com.knowledge.myfinapp.data.mappers

import com.knowledge.myfinapp.data.remote.dto.MerchantDto
import com.knowledge.myfinapp.domain.model.Merchant

fun Merchant.toDto(): MerchantDto =
    MerchantDto(
        id = id,
        name = name,
        normalizedName = normalizedName
    )