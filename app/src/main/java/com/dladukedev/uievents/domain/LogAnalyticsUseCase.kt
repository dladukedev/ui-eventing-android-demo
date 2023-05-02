package com.dladukedev.uievents.domain

import android.util.Log

interface LogAnalyticsUseCase {
    operator fun invoke(message: String)
}
class LogcatLogAnalyticsUseCase: LogAnalyticsUseCase {
    override fun invoke(message: String) {
        Log.i("Analytics", message)
    }
}