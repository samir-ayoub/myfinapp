package com.knowledge.myfinapp.system.di

import com.knowledge.myfinapp.data.category.heuristic.CategoryHeuristic
import com.knowledge.myfinapp.data.category.repository.CategoryRepository
import com.knowledge.myfinapp.data.notification.impl.BankDetectorImpl
import com.knowledge.myfinapp.data.notification.impl.NotificationParserImpl
import com.knowledge.myfinapp.data.notification.impl.AmountExtractorImpl
import com.knowledge.myfinapp.data.notification.impl.ExpenseBuilderImpl
import com.knowledge.myfinapp.data.notification.impl.MerchantNormalizerImpl
import com.knowledge.myfinapp.data.notification.parser.AmountExtractor
import com.knowledge.myfinapp.data.notification.parser.BankDetector
import com.knowledge.myfinapp.data.notification.parser.ExpenseBuilder
import com.knowledge.myfinapp.data.notification.parser.MerchantNormalizer
import com.knowledge.myfinapp.data.notification.parser.NotificationParser
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
     fun provideExpenseBuilder(
        categoryHeuristic: CategoryHeuristic,
        categoryRepository: CategoryRepository
     ): ExpenseBuilder = ExpenseBuilderImpl(
        categoryHeuristic,
         categoryRepository
     )
}