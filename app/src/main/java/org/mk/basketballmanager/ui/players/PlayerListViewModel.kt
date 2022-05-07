package org.mk.basketballmanager.ui.players

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mk.basketballmanager.firebase.FirebaseDBManager
import org.mk.basketballmanager.models.PlayerModel
import org.mk.basketballmanager.models.TeamModel
import timber.log.Timber

class PlayerListViewModel : ViewModel() {
    private val players = MutableLiveData<List<PlayerModel>>()

    val observablePlayers: LiveData<List<PlayerModel>>
        get() = players

    init{ load() }

    fun load(){
        try{
            FirebaseDBManager.findAllPlayers(players)
            Timber.i("Players load() success: ${players.value.toString()}")
        }
        catch(e: Exception){
            Timber.i("Players load() error: ${e.message}")
        }
    }

    fun delete(id: String, player: PlayerModel) {
        // Return if team id is not empty.
        // This should not happen, but this guards against it
        if(player.teamID.isNotEmpty()){
            return
        }
        try{
            FirebaseDBManager.findAllPlayers(players)
            Timber.i("Players load() success: ${players.value.toString()}")
        }
        catch(e: Exception){
            Timber.i("Players load() error: ${e.message}")
        }
    }
}