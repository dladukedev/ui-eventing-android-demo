package com.dladukedev.uievents.ui.core

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(
    onClickCallbacks: () -> Unit,
    onClickMarkOnSend: () -> Unit,
    onClickFireAndForget: () -> Unit,
    onClickMarkOnConsume: () -> Unit,
    onClickState: () -> Unit,
) {
    Column {
        Button(onClick = onClickCallbacks) {
            Text(text = "Callbacks")
        }
        Button(onClick = onClickMarkOnSend) {
            Text(text = "Mark on Send")
        }
        Button(onClick = onClickFireAndForget) {
            Text(text = "Fire and Forget")
        }
        Button(onClick = onClickMarkOnConsume) {
            Text(text = "Mark on Consume")
        }
        Button(onClick = onClickState) {
            Text(text = "State")
        }
    }
}