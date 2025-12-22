package com.knowledge.myfinapp.system.di

import com.knowledge.myfinapp.data.notification.delegate.BankDetectorImpl
import com.knowledge.myfinapp.data.notification.delegate.NotificationParserImpl
import com.knowledge.myfinapp.data.notification.delegate.AmountExtractorImpl
import com.knowledge.myfinapp.data.notification.delegate.ExpenseBuilderImpl
import com.knowledge.myfinapp.data.notification.delegate.MerchantNormalizerImpl
import com.knowledge.myfinapp.data.notification.delegate.AmountExtractor
import com.knowledge.myfinapp.data.notification.delegate.BankDetector
import com.knowledge.myfinapp.data.notification.delegate.ExpenseBuilder
import com.knowledge.myfinapp.data.notification.delegate.MerchantNormalizer
import com.knowledge.myfinapp.data.notification.delegate.NotificationParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationParserModule {

    @Provides
    @Singleton
    fun provideNotificationParser(
        bankDetector: BankDetector,
        amountExtractor: AmountExtractor,
        merchantNormalizer: MerchantNormalizer
    ): NotificationParser = NotificationParserImpl(bankDetector, amountExtractor, merchantNormalizer)

    @Provides
    @Singleton
    fun provideBankDetector(): BankDetector = BankDetectorImpl()

    @Provides
    @Singleton
    fun provideAmountExtractor(): AmountExtractor = AmountExtractorImpl()

    @Provides
    @Singleton
     fun provideMerchantNormalizer(): MerchantNormalizer = MerchantNormalizerImpl()

    @Provides
    @Singleton
     fun provideExpenseBuilder(): ExpenseBuilder = ExpenseBuilderImpl()
}