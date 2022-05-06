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


    fun load(){
        try{
            FirebaseDBManager.findAllFreePlayers(freeAgents)
            Timber.i("FreeAgent load() success: ${freeAgents.value.toString()}")
        }
        catch(e: Exception){
            Timber.i("FreeAgent load() error: ${e.message}")
        }
    }
}