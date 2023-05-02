package com.dladukedev.uievents.ui.events

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dladukedev.uievents.domain.LogAnalyticsUseCase
import com.dladukedev.uievents.domain.LogcatLogAnalyticsUseCase
import com.dladukedev.uievents.ui.core.EventScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MarkOnSendViewModel(
    private val logAnalytics: LogAnalyticsUseCase = LogcatLogAnalyticsUseCase()
) : ViewModel() {
    private val _showSnackbarEvent = MutableStateFlow(false)
    private val _showDetailsEvent = MutableStateFlow<Int?>(null)

    val showSnackbarEvent = _showSnackbarEvent.asStateFlow()
        .transform { showSnackbar ->
            if(showSnackbar) {
                _showSnackbarEvent.value = false
                emit(true)
            }
        }

    val showDetailsEvent = _showDetailsEvent.asStateFlow()
        .transform {detailsId ->
            if(detailsId  != null) {
                _showDetailsEvent.value = null
                emit(detailsId)
            }
        }

    fun onDetailsSelected(detailsId: Int) {
        logAnalytics("Selected Details $detailsId")
        _showDetailsEvent.update { detailsId }
    }

    fun onShowToast() {
        logAnalytics("Show Toast")
        _showSnackbarEvent.update { true }
    }
}

@Composable
fun MarkOnSendScreen(goToDetails: (Int) -> Unit, vm: MarkOnSendViewModel = viewModel()) {
    val showSnackbar by vm.showSnackbarEvent.collectAsState(false)
    val showDetails by vm.showDetailsEvent.collectAsState(null)

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = showSnackbar) {
        if (showSnackbar) {
            scope.launch {
                snackbarHostState.showSnackbar(message = "Snackbar Test")
            }
        }
    }
    LaunchedEffect(key1 = showDetails) {
        showDetails?.let { detailsId ->
            goToDetails(detailsId)
        }
    }

    EventScreen(
        snackbarHostState = snackbarHostState,
        onDetailsSelected = vm::onDetailsSelected,
        onToastRequested = vm::onShowToast
    )
}