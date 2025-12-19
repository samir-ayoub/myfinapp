package com.knowledge.myfinapp.ui.onboarding

import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.knowledge.myfinapp.core.permissions.NotificationPermissionChecker
import com.knowledge.myfinapp.core.permissions.NotificationPermissionNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationPermissionViewModel @Inject constructor(
    private val checker: NotificationPermissionChecker,
    private val navigator: NotificationPermissionNavigator
): ViewModel() {

    private val _state = MutableStateFlow(checker.hasPermission())
    val state: StateFlow<Boolean> = _state

    fun onGrantPermissionClicked() {
        navigator.openSettings()
    }

    fun onResume() {
        _state.value = checker.hasPermission()
        Timber.i("Permission checker permission status: ${_state.value}")
    }
}