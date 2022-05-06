package org.mk.basketballmanager.ui.players

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import org.mk.basketballmanager.firebase.FirebaseDBManager
import org.mk.basketballmanager.models.PlayerModel
import timber.log.Timber

class RosterViewModel : ViewModel() {
    private val roster = MutableLiveData<List<PlayerModel>>()
    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    val observableRoster: LiveData<List<PlayerModel>>
        get() = roster


    fun load(){
        try{
            liveFirebaseUser.value?.let {
                FirebaseDBManager.getRoster(it.uid, roster)
                Timber.i("Roster load() success: ${roster.value.toString()}")
            } ?: run{
                Timber.i("Roster load() error: user id is null")
            }
        }
        catch(e: Exception){
            Timber.i("Roster load() error: ${e.message}")
        }
    }
}