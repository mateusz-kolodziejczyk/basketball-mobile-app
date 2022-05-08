package org.mk.basketballmanager.ui.playerdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import org.mk.basketballmanager.enums.Position
import org.mk.basketballmanager.firebase.FirebaseDBManager
import org.mk.basketballmanager.firebase.FirebaseImageManager
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
            FirebaseDBManager.findPlayerByID(id, player)
            Timber.i("Team Detail update() success: $player")
        }
        catch(e: Exception){
            Timber.i("Team get() error: ${e.message}")
        }
    }

    fun updatePlayer() {
        try{
            player.value?.let {
                FirebaseDBManager.updatePlayer(it)
                FirebaseImageManager.updatePlayerImage(it)
                Timber.i("Team Detail update() success: ${player.value}")
            }
        }
        catch(e: Exception){
            Timber.i("Team Detail update() error: ${e.message}")
        }
    }

    fun updateImage(image: String){
        player.value?.let {
            it.image = image
            player.value = it
        }
    }

    fun updatePosition(position: Position){
        player.value?.let{
            it.position = position
            player.value = it
        }
    }
}