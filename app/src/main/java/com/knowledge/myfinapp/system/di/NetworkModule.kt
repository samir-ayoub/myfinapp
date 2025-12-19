package com.knowledge.myfinapp.system.di

import com.knowledge.myfinapp.data.remote.api.Adapters
import com.knowledge.myfinapp.data.remote.api.ExpenseApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(Adapters()) // Moshi works upon this
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.myfinapp.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides
    @Singleton
    fun provideExpenseApi(retrofit: Retrofit): ExpenseApi =
        retrofit.create(ExpenseApi::class.java)

}