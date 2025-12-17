package com.knowledge.myfinapp.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.syncDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "sync_store"
)