package org.mk.basketballmanager.ui.players

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mk.basketballmanager.firebase.FirebaseDBManager
import org.mk.basketballmanager.models.PlayerModel
import timber.log.Timber

class FreeAgentListViewModel : ViewModel() {
    private val freeAgents = MutableLiveData<List<PlayerModel>>()

    val observableFreeAgents: LiveData<List<PlayerModel>>
        get() = freeAgents

    init{ load() }


    fun load(){
        try{
            FirebaseDBManager.findAllFreePlayers(freeAgents)
            Timber.i("FreeAgent load() success: ${freeAgents.value.toString()}")
        }
        catch(e: Exception){
            Timber.i("FreeAgent load() error: ${e.message}")
        }
    }

    fun delete(id: String, player: PlayerModel) {
        // Return if team id is not empty.
        // This should not happen, but this guards against it
        if(player.teamID.isNotEmpty()){
            return
        }
        try{
            FirebaseDBManager.deletePlayer(id)
            Timber.i("Playre Delete() success")
        }
        catch(e: Exception){
            Timber.i("Players Delete() error: ${e.message}")
        }
    }

    fun addToRoster(userID: String, player: PlayerModel) {
        try{
            FirebaseDBManager.addPlayerToRoster(userID, player)
            Timber.i("Player Roster Add() success: $player")
        }
        catch(e: Exception){
            Timber.i("Players Delete() error: ${e.message}")
        }
    }
}