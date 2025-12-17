package com.knowledge.myfinapp.data.sync

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.knowledge.myfinapp.data.datastore.syncDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import java.time.Instant

class SyncStoreImpl @Inject constructor(@param:ApplicationContext private val context: Context): SyncStore {
    override suspend fun lastSyncTime(): Instant? {
        val prefs = context.syncDataStore.data.first()
        val millis = prefs[LAST_SYNC_KEY]
        return millis?.let { Instant.ofEpochMilli(it) }
    }

    override suspend fun updateLastSync(time: Instant) {
        context.syncDataStore.edit { prefs -> prefs[LAST_SYNC_KEY] = time.toEpochMilli() }
    }

    companion object {
        private val LAST_SYNC_KEY = longPreferencesKey("last_sync_time")
    }
}