package com.dladukedev.uievents.ui.events

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dladukedev.uievents.domain.AnalyticsUseCase
import com.dladukedev.uievents.domain.AnalyticsUseCaseImpl
import com.dladukedev.uievents.ui.core.EventScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class MarkOnConsumeViewModel(
    private val logAnalytics: AnalyticsUseCase = AnalyticsUseCaseImpl()
) : ViewModel() {
    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events = _events.asStateFlow()

    sealed class Event {
        open var eventId: Int? = null

        object ShowToast : Event()
        data class ShowDetails(val id: Int) : Event()
    }

    fun onDetailsSelected(detailsId: Int) {
        logAnalytics("Selected Details $detailsId")
        postEvent(Event.ShowDetails(detailsId))
    }

    fun onShowToast() {
        logAnalytics("Show Toast")
        postEvent(Event.ShowToast)
    }

    private fun postEvent(event: Event) {
        event.eventId = Random.nextInt()
        _events.update { current -> current + event }
    }

    fun markEventHandled(event: Event) {
        _events.update { current ->
            current.filterNot { it.eventId == event.eventId }
        }
    }

}

@Composable
fun MarkOnConsumeScreen(goToDetails: (Int) -> Unit, vm: MarkOnConsumeViewModel = viewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        vm.events
            .map { events -> events.firstOrNull() }
            .filterNotNull()
            .collect { event ->
                when (event) {
                    is MarkOnConsumeViewModel.Event.ShowDetails -> goToDetails(event.id)
                    MarkOnConsumeViewModel.Event.ShowToast -> scope.launch {
                        snackbarHostState.showSnackbar(message = "Snackbar Test")
                    }
                }
                vm.markEventHandled(event)
            }
    }

    EventScreen(
        snackbarHostState = snackbarHostState,
        onDetailsSelected = vm::onDetailsSelected,
        onToastRequested = vm::onShowToast
    )
}