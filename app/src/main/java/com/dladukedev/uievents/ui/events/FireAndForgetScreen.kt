package com.dladukedev.uievents.ui.events

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dladukedev.uievents.domain.LogAnalyticsUseCase
import com.dladukedev.uievents.domain.LogcatLogAnalyticsUseCase
import com.dladukedev.uievents.ui.core.EventScreen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class FireAndForgetViewModel (
    private val logAnalytics: LogAnalyticsUseCase = LogcatLogAnalyticsUseCase()
) : ViewModel() {
    private val _events = Channel<Event>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    sealed class Event {
        object ShowToast : Event()
        data class ShowDetails(val id: Int) : Event()
    }

    fun onDetailsSelected(detailsId: Int) {
        logAnalytics("Selected Details $detailsId")
        viewModelScope.launch {
            _events.send(Event.ShowDetails(detailsId))
        }
    }

    fun onShowToast() {
        logAnalytics("Show Toast")
        viewModelScope.launch {
            _events.send(Event.ShowToast)
        }
    }
}

@Composable
fun FireAndForgetScreen(goToDetails: (Int) -> Unit, vm: FireAndForgetViewModel = viewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            vm.events.collect { event ->
                when (event) {
                    is FireAndForgetViewModel.Event.ShowDetails -> goToDetails(event.id)
                    FireAndForgetViewModel.Event.ShowToast -> launch {
                        snackbarHostState.showSnackbar(message = "Snackbar Test")
                    }
                }
            }
        }
    }

    EventScreen(
        snackbarHostState = snackbarHostState,
        onDetailsSelected = vm::onDetailsSelected,
        onToastRequested = vm::onShowToast
    )

}