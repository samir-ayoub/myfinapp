package com.knowledge.myfinapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.knowledge.myfinapp.ui.home.HomeScreen
import com.knowledge.myfinapp.ui.onboarding.NotificationPermissionScreen
import com.knowledge.myfinapp.ui.onboarding.NotificationPermissionViewModel
import timber.log.Timber

@Composable
fun AppEntry(
    viewModel: NotificationPermissionViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.onResume()
        }
    }

    val hasPermission by viewModel.state.collectAsState()
    Timber.i("App has permission to listen for notifications: $hasPermission")

    if (hasPermission) {
        HomeScreen()
    } else {
        NotificationPermissionScreen(viewModel)
    }
}