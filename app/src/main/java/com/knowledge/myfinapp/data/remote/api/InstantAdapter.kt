package com.knowledge.myfinapp.data.remote.api

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.Instant

class InstantAdapter {
    @ToJson
    fun toJson(instant: Instant?): Long? = instant?.toEpochMilli()

    @FromJson
    fun fromJson(value: Long?): Instant? = value?.let { Instant.ofEpochMilli(it) }
}