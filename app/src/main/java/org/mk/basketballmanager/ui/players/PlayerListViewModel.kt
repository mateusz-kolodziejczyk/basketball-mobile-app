package org.mk.basketballmanager.ui.players

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mk.basketballmanager.firebase.FirebaseDBManager
import org.mk.basketballmanager.models.PlayerModel
import timber.log.Timber

class PlayerListViewModel : ViewModel() {
    private val players = MutableLiveData<List<PlayerModel>>()

    val observablePlayers: LiveData<List<PlayerModel>>
        get() = players


    fun load(){
        try{
            FirebaseDBManager.findAllPlayers(players)
            Timber.i("Players load() success: ${players.value.toString()}")
        }
        catch(e: Exception){
            Timber.i("Players load() error: ${e.message}")
        }
    }
}