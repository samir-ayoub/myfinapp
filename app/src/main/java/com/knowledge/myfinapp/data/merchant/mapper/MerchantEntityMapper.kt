package com.knowledge.myfinapp.data.merchant.mapper

import com.knowledge.myfinapp.data.merchant.model.MerchantEntity
import com.knowledge.myfinapp.domain.model.Merchant

fun  MerchantEntity.toDomain(): Merchant =
    Merchant(
        id = id,
        name = name,
        normalizedName = normalizedName
    )

fun Merchant.toEntity(): MerchantEntity =
    MerchantEntity(
        id = id,
        name = name,
        normalizedName = normalizedName
    )