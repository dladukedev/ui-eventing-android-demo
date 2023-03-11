package com.dladukedev.uievents.domain

import android.util.Log

interface AnalyticsUseCase {
    operator fun invoke(message: String)
}
class AnalyticsUseCaseImpl: AnalyticsUseCase {
    override fun invoke(message: String) {
        Log.i("Analytics", message)
    }
}