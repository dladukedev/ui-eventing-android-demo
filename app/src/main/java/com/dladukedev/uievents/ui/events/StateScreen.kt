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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StateViewModel(
    private val logAnalytics: LogAnalyticsUseCase = LogcatLogAnalyticsUseCase()
) : ViewModel() {
    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    data class State(
        val showToast: Boolean = false,
        val selectedDetails: Int? = null,
    )

    fun onDetailsSelected(detailsId: Int) {
        logAnalytics("Selected Details $detailsId")
        _state.update { current -> current.copy(selectedDetails = detailsId) }
    }

    fun onShowToast() {
        logAnalytics("Show Toast")
        _state.update { current -> current.copy(showToast = true) }
    }

    fun unselectDetails() {
        _state.update { current -> current.copy(selectedDetails = null) }
    }

    fun unshowToast() {
        _state.update { current -> current.copy(showToast = false) }
    }
}

@Composable
fun StateScreen(goToDetails: (Int) -> Unit, vm: StateViewModel = viewModel()) {
    val state by vm.state.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = state) {
        val currentState = state
        when {
            currentState.showToast -> {
                scope.launch {
                    snackbarHostState.showSnackbar(message = "Snackbar Test")
                }
                vm.unshowToast()
            }

            currentState.selectedDetails != null -> {
                goToDetails(currentState.selectedDetails)
                vm.unselectDetails()
            }
        }
    }

    EventScreen(
        snackbarHostState = snackbarHostState,
        onDetailsSelected = vm::onDetailsSelected,
        onToastRequested = vm::onShowToast
    )
}