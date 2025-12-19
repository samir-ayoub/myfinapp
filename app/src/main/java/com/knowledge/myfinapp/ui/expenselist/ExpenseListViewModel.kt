package com.knowledge.myfinapp.ui.expenselist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.knowledge.myfinapp.domain.repository.ExpenseLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val expenseLocalRepository: ExpenseLocalRepository,
): ViewModel() {
    val uiExpenses = expenseLocalRepository.observeExpenses()
        .map { expenses ->
            expenses.map { it.toUiExpense() }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )
}