package org.mk.basketballmanager.ui.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mk.basketballmanager.firebase.FirebaseDBManager
import org.mk.basketballmanager.models.PlayerModel
import org.mk.basketballmanager.models.TeamModel
import timber.log.Timber

class TeamListViewModel : ViewModel() {
    private val teams = MutableLiveData<List<TeamModel>>()

    val observableTeams: LiveData<List<TeamModel>>
        get() = teams

    init{ load() }

    fun load(){
        try{
            FirebaseDBManager.findAllTeams(teams)
            Timber.i("Players load() success: ${teams.value.toString()}")
        }
        catch(e: Exception){
            Timber.i("Players load() error: ${e.message}")
        }
    }
}