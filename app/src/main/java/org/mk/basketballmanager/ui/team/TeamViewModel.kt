package org.mk.basketballmanager.ui.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.donationx.firebase.FirebaseDBManager
import org.mk.basketballmanager.models.TeamModel
import timber.log.Timber


// Loads team from database that corresponds to the user id
class TeamViewModel : ViewModel() {
    private val team = MutableLiveData<TeamModel>()
    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    var observableTeam: LiveData<TeamModel>
        get() = team
        set(value) {team.value = value.value}

    fun createTeam(){
        try{
            liveFirebaseUser.value?.let {
                FirebaseDBManager.createTeam(it.uid, TeamModel());
                Timber.i("Team create() success: ${team.value}")
            } ?: run {
                Timber.i("Team create() error: user id is null")
            }
        }
        catch(e: Exception){
            Timber.i("Team create() error: ${e.message}")
        }
    }
    fun getTeam(){
        try{
            liveFirebaseUser.value?.let {
                FirebaseDBManager.findTeamByID(it.uid, team)
                Timber.i("Team get() success: ${team.value}")
            } ?: run {
                Timber.i("Team get() error: user id is null")
            }
        }
        catch(e: Exception){
            Timber.i("Team get() error: ${e.message}")
        }
    }

    fun updateTeam(team: TeamModel){
        try{
            liveFirebaseUser.value?.let {
                FirebaseDBManager.updateTeam(it.uid, team)
                Timber.i("Team Detail update() success: $team")
            } ?: run{
                Timber.i("Team Detail update() error: user id is null")
            }
        }
        catch(e: Exception){
            Timber.i("Team Detail update() error: ${e.message}")
        }
    }

}