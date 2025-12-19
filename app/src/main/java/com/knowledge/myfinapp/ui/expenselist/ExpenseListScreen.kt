package com.knowledge.myfinapp.ui.expenselist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import java.time.format.DateTimeFormatter

@Composable
@Preview
fun ExpenseListScreen(
    viewModel: ExpenseListViewModel = hiltViewModel()
) {
    val expenses by viewModel.uiExpenses.collectAsState()

    Scaffold { paddingValues ->
        ExpenseList(expenses, paddingValues)
    }
}

@Composable
fun ExpenseList(expenses: List<UiExpense>, paddingValues: PaddingValues) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues
    ) {
        items(expenses) { expense ->    // <--- aqui!
            ExpenseRow(expense)
            HorizontalDivider()
        }
    }
}

@Composable
fun ExpenseRow(expense: UiExpense) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Default.AttachMoney, // ou outro icone
            contentDescription = "Category Icon",
            modifier = Modifier.size(40.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = expense.description,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = expense.occurredAt.format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            text = "${expense.amount} €",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}