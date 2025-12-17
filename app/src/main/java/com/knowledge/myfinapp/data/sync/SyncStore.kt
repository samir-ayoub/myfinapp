package com.knowledge.myfinapp.data.sync

import java.time.Instant

interface SyncStore {
    suspend fun lastSyncTime(): Instant?

    suspend fun updateLastSync(time: Instant)
}