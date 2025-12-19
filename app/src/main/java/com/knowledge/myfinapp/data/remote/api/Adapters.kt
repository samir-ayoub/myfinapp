package com.knowledge.myfinapp.data.remote.api

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.math.BigDecimal
import java.time.Instant

class Adapters {
    @ToJson
    fun toJson(instant: Instant?): Long? = instant?.toEpochMilli()

    @FromJson
    fun fromJson(value: Long?): Instant? = value?.let { Instant.ofEpochMilli(it) }

    @ToJson
    fun toJson(value: BigDecimal): String =
        value.toPlainString()

    @FromJson
    fun fromJson(value: String): BigDecimal =
        value.toBigDecimal()
}