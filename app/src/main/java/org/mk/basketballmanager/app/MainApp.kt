package org.mk.basketballmanager.app

import android.app.Application
import org.mk.basketballmanager.enums.Position
import org.mk.basketballmanager.models.datastores.TeamStore
import org.mk.basketballmanager.models.Location
import org.mk.basketballmanager.models.PlayerModel
import org.mk.basketballmanager.models.TeamModel
import org.mk.basketballmanager.models.datastores.PlayerStore
import timber.log.Timber
import timber.log.Timber.i
import java.util.*

class MainApp : Application() {
    lateinit var players: PlayerStore
    lateinit var teams: TeamStore
    // This is only for testing, later on a team will be selected using the current user instead.
    lateinit var currentTeam: TeamModel
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("BasketballManager Application Started")
        players = PlayerStore(this)
        teams = TeamStore(this)
    }
}