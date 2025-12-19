package com.knowledge.myfinapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.knowledge.myfinapp.viewmodel.ExpensesViewModel

@Composable
@Preview
fun HomeScreen(
    viewModel: ExpensesViewModel = hiltViewModel()
) {
    val expenses by viewModel.expenses.collectAsState()

    Scaffold { paddingValues ->
        Column(
            modifier= Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            expenses.forEach { expense ->
                Row {
                    Text(text = "${expense.description} - ${expense.amount}")
                }
            }
        }
    }
}