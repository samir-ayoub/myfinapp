package com.knowledge.myfinapp.system.di

import com.knowledge.myfinapp.data.repository.MerchantRepository
import com.knowledge.myfinapp.data.repository.MerchantRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MerchantModule {

    @Binds
    @Singleton
    abstract fun bindMerchantRepository(
        impl: MerchantRepositoryImpl
    ): MerchantRepository
}