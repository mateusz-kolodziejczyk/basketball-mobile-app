package org.mk.basketballmanager.ui.playerdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import org.mk.basketballmanager.models.PlayerModel
import timber.log.Timber

class PlayerDetailViewModel : ViewModel() {
    private val player = MutableLiveData<PlayerModel>()
    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    var observablePlayer: LiveData<PlayerModel>
        get() = player
        set(value) {player.value = value.value}

    fun getPlayer(id: String){
        try{
            Timber.i("Team Detail update() success: $player")
        }
        catch(e: Exception){
            Timber.i("Team get() error: ${e.message}")
        }
    }

    fun updateTeam(userID: String, id: String, player: PlayerModel){
        try{
                Timber.i("Team Detail update() success: $player")
        }
        catch(e: Exception){
            Timber.i("Team Detail update() error: ${e.message}")
        }
    }

}