package com.chatflow.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChatFlowApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
