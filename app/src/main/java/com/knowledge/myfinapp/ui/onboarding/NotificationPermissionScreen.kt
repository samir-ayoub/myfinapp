package com.knowledge.myfinapp.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun NotificationPermissionScreen(
    viewModel: NotificationPermissionViewModel = hiltViewModel()
) {
    val hasPermission by viewModel.state.collectAsState()

    Scaffold { paddingValues ->
        Column(
            modifier= Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

        ) {
            Text("My FinApp needs to access your bank notifications")

            if (!hasPermission) {
                Button(
                    onClick = { viewModel.onGrantPermissionClicked() }
                ) {
                    Text("Allow access to notification listener")
                }
            } else {
                Text("Permission granted ✅")
            }
        }
    }
}