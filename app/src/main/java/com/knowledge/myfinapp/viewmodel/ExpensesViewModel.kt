package com.knowledge.myfinapp.viewmodel

import androidx.lifecycle.ViewModel
import com.knowledge.myfinapp.domain.usecase.GetExpensesUseCase
import com.knowledge.myfinapp.domain.usecase.SyncExpensesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val getExpenses: GetExpensesUseCase,
    private val syncExpenses: SyncExpensesUseCase
): ViewModel() {
}