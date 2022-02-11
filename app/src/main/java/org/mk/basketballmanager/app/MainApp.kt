package org.mk.basketballmanager.app

import android.app.Application
import org.mk.basketballmanager.models.datastores.TeamStore
import org.mk.basketballmanager.helpers.addSampleData
import org.mk.basketballmanager.models.datastores.PlayerStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {
    lateinit var players: PlayerStore
    override fun onCreate() {
        var addSampleData = false

        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Basketball App started")
        players = PlayerStore(this)
        // App gets a new spotify access token each time it launches

        // Add sample data to the app.
        if(addSampleData){
            addSampleData(players)
        }
    }
}