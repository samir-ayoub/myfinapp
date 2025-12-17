package com.knowledge.myfinapp.system.di

import com.knowledge.myfinapp.data.sync.SyncStore
import com.knowledge.myfinapp.data.sync.SyncStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SyncModule {

    @Binds
    abstract fun bindSyncStore(
        impl: SyncStoreImpl
    ): SyncStore
}