package com.dladukedev.uievents.ui.core

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(snackbarHostState: SnackbarHostState, onDetailsSelected: (Int) -> Unit, onToastRequested: () -> Unit) {
    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Button(onClick = { onDetailsSelected(1) }) {
                Text(text = "Go to Details 1")
            }
            Button(onClick = { onDetailsSelected(2) }) {
                Text(text = "Go to Details 2")
            }
            Button(onClick = { onToastRequested() }) {
                Text(text = "Show Snackbar")
            }
        }
    }
}