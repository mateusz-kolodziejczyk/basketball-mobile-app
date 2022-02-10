package org.mk.basketballmanager.app

import android.app.Application
import android.media.session.MediaController
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import org.mk.basketballmanager.models.datastores.PlayerStore
import org.mk.playlist.BuildConfig
import org.mk.playlist.helpers.addSampleData
import org.mk.playlist.helpers.createTokenRequest
import org.mk.playlist.models.data_stores.ArtistStore
import org.mk.playlist.models.data_stores.PlaylistStore
import org.mk.playlist.models.data_stores.TrackStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {
    lateinit var players: PlayerStore
    override fun onCreate() {
        var addSampleData = true

        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Playlist App started")
        players = PlayerStore(this)
        // App gets a new spotify access token each time it launches

        // Add sample data to the app.
        if(addSampleData){
            addSampleData(players)
        }
    }
}