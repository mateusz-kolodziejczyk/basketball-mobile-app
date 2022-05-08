package org.mk.basketballmanager.ui.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import org.mk.basketballmanager.firebase.FirebaseDBManager
import org.mk.basketballmanager.firebase.FirebaseImageManager
import org.mk.basketballmanager.models.TeamModel
import timber.log.Timber

class TeamDetailViewModel : ViewModel() {
    private val team = MutableLiveData<TeamModel>()

    var observableTeam: LiveData<TeamModel>
        get() = team
        set(value) {team.value = value.value}


    fun reset(){

    }
    fun updateImage(image: String){
        team.value?.let {
            it.image = image
            team.value = it
        }
    }
    fun getTeam(id: String){
        try{
            FirebaseDBManager.findTeamByID(id, team)
            Timber.i("Team get() success: ${team.value}")
        }
        catch(e: Exception){
            Timber.i("Team get() error: ${e.message}")
        }
    }

    fun updateTeam(){
        try{
            team.value?.let {
                FirebaseDBManager.updateTeam(it.id, it)
                FirebaseImageManager.updateTeamImage(it)
                Timber.i("Team Detail update() success: $team")
            }
        }
        catch(e: Exception){
            Timber.i("Team Detail update() error: ${e.message}")
        }
    }
}