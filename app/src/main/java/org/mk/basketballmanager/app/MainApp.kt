package org.mk.basketballmanager.app

import android.app.Application
import org.mk.basketballmanager.enums.Position
import org.mk.basketballmanager.models.datastores.TeamStore
import org.mk.basketballmanager.helpers.addSampleData
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
        val addSampleData = true

        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Basketball App started")
        players = PlayerStore(this)
        teams = TeamStore(this)

        teams.deleteAll()
        val teamToAdd = TeamModel(UUID.randomUUID(), "Waterford Vikings", Location(52.25640225072533, -7.122288253745915))
        teams.add(teamToAdd)
        // App gets a new spotify access token each time it launches
        currentTeam = teamToAdd
        // Add sample data to the app.
        if(addSampleData){
            players.deleteAll()
            addSampleData(players)
            val samplePlayer = PlayerModel(UUID.randomUUID(), "Lebron James", Position.SmallForward)
            players.add(samplePlayer)
            teams.addUpdatePlayer(currentTeam, samplePlayer, samplePlayer.preferredPosition)
        }
    }
}