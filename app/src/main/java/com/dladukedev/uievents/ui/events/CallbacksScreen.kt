package com.dladukedev.uievents.ui.events

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dladukedev.uievents.domain.AnalyticsUseCase
import com.dladukedev.uievents.domain.AnalyticsUseCaseImpl
import com.dladukedev.uievents.ui.core.EventScreen
import kotlinx.coroutines.launch

class CallbacksViewModel(
    private val logAnalytics: AnalyticsUseCase = AnalyticsUseCaseImpl()
) : ViewModel() {
    private var callbacks: Callbacks? = null

    fun onDetailsSelected(detailsId: Int) {
        logAnalytics("Selected Details $detailsId")
        callbacks?.onDetailsCallback(detailsId)
    }

    fun onShowToast() {
        logAnalytics("Show Toast")
        callbacks?.onShowToastCallback()
    }

    fun registerCallbacks(newCallbacks: Callbacks) {
        callbacks = newCallbacks
    }

    fun unregisterCallbacks() {
        callbacks = null
    }

    interface Callbacks {
        fun onShowToastCallback()
        fun onDetailsCallback(id: Int)
    }
}

@Composable
fun CallbacksScreen(goToDetails: (Int) -> Unit, vm: CallbacksViewModel = viewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        vm.registerCallbacks(object : CallbacksViewModel.Callbacks {
            override fun onShowToastCallback() {
                scope.launch { snackbarHostState.showSnackbar(message = "Snackbar Test") }
            }

            override fun onDetailsCallback(id: Int) {
                goToDetails(id)
            }
        })

        onDispose {
            vm.unregisterCallbacks()
        }
    }

    EventScreen(
        snackbarHostState = snackbarHostState,
        onDetailsSelected = vm::onDetailsSelected,
        onToastRequested = vm::onShowToast
    )
}