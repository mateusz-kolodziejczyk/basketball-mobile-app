package org.mk.basketballmanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mk.basketballmanager.models.TeamModel

class TeamViewModel : ViewModel() {
    private val selectedTeam = MutableLiveData<TeamModel>()
    fun selectTeam(team: TeamModel){
        selectedTeam.value = team
    }
    fun getSelectedTeam(): LiveData<TeamModel>{
        return selectedTeam
    }
}