package com.knowledge.myfinapp.mocks.fakedata

import java.time.Instant

object FakeInstant {
    val FIXED_TIME_1: Instant = Instant.parse("2025-12-17T12:00:00Z")
    val FIXED_TIME_2: Instant = Instant.parse("2025-12-17T12:01:00Z")
    val FIXED_TIME_3: Instant = Instant.parse("2025-12-17T12:05:00Z")

    // Função helper para gerar instant incrementando segundos
    fun plusSeconds(base: Instant = FIXED_TIME_1, seconds: Long): Instant =
        base.plusSeconds(seconds)

    // Função helper para gerar lista de instants incrementais
    fun generateSequence(count: Int, start: Instant = FIXED_TIME_1, stepSeconds: Long = 60): List<Instant> =
        (0 until count).map { start.plusSeconds(it * stepSeconds) }
}
