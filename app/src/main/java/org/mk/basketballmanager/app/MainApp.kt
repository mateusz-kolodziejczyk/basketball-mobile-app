package org.mk.basketballmanager.app

import android.app.Application
import timber.log.Timber

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("BasketballManager Application Started")
    }
}