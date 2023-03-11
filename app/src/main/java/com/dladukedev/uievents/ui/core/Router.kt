package com.dladukedev.uievents.ui.core

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dladukedev.uievents.ui.events.CallbacksScreen
import com.dladukedev.uievents.ui.events.FireAndForgetScreen
import com.dladukedev.uievents.ui.events.MarkOnConsumeScreen
import com.dladukedev.uievents.ui.events.MarkOnSendScreen
import com.dladukedev.uievents.ui.events.StateScreen

@Composable
fun Router(navHostController: NavHostController = rememberNavController()) {
    NavHost(navController = navHostController, startDestination = "home") {
        composable(route = "home") {
            HomeScreen(
                onClickCallbacks = { navHostController.navigate("callbacks") },
                onClickMarkOnSend = { navHostController.navigate("mark-on-send") },
                onClickFireAndForget = { navHostController.navigate("fire-and-forget") },
                onClickMarkOnConsume = { navHostController.navigate("mark-on-consume") },
                onClickState = { navHostController.navigate("state") })
        }
        composable(route = "callbacks") {
            CallbacksScreen(goToDetails = { id -> navHostController.navigate("details/$id") })
        }
        composable(route = "mark-on-send") {
            MarkOnSendScreen(goToDetails = { id -> navHostController.navigate("details/$id") })
        }
        composable(route = "fire-and-forget") {
            FireAndForgetScreen(goToDetails = { id -> navHostController.navigate("details/$id") })
        }
        composable(route = "mark-on-consume") {
            MarkOnConsumeScreen(goToDetails = { id -> navHostController.navigate("details/$id") })
        }
        composable(route = "state") {
            StateScreen(goToDetails = { id -> navHostController.navigate("details/$id") })
        }

        composable(route = "details/{id}") {
            it.arguments?.getString("id")?.let { id ->
                DetailsScreen(id = id)
            }
        }
    }
}