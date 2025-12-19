package com.knowledge.myfinapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knowledge.myfinapp.domain.repository.ExpenseLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val expenseLocalRepository: ExpenseLocalRepository,
): ViewModel() {
    val expenses = expenseLocalRepository.observeExpenses()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )
}