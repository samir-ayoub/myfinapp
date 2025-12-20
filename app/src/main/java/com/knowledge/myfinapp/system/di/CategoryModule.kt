package com.knowledge.myfinapp.system.di

import com.knowledge.myfinapp.data.category.heuristic.CategoryHeuristic
import com.knowledge.myfinapp.data.category.heuristic.CategoryHeuristicImpl
import com.knowledge.myfinapp.data.category.repository.CategoryRepository
import com.knowledge.myfinapp.data.category.repository.CategoryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import jakarta.inject.Singleton
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryModule {

    @Binds
    @Singleton
    abstract fun bindCategoryHeuristic(
        impl: CategoryHeuristicImpl
    ): CategoryHeuristic

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        impl: CategoryRepositoryImpl
    ): CategoryRepository
}