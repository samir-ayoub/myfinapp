package com.knowledge.myfinapp.data.local.converter

import androidx.room.TypeConverter
import java.math.BigDecimal
import kotlin.text.toBigDecimal

class BigDecimalConverter {
    @TypeConverter
    fun fromBigDecimal(value: BigDecimal?): String? = value?.toPlainString()

    @TypeConverter
    fun toBigDecimal(value: String?): BigDecimal? = value?.let {BigDecimal(it)}
}