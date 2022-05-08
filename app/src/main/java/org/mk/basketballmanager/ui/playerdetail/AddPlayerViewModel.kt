package org.mk.basketballmanager.ui.playerdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mk.basketballmanager.enums.Position
import org.mk.basketballmanager.firebase.FirebaseDBManager
import org.mk.basketballmanager.firebase.FirebaseImageManager
import org.mk.basketballmanager.models.PlayerModel

class AddPlayerViewModel : ViewModel() {
    private val status = MutableLiveData<Boolean>()
    private val player = MutableLiveData<PlayerModel>()
    val observableStatus: LiveData<Boolean>
        get() = status
    var observablePlayer: LiveData<PlayerModel>
        get() = player
        set(value) { player.value = value.value}

    init {
        reset()
    }

    fun reset(){
        player.value = PlayerModel()
        status.value = false
    }
    fun addPlayer() {
        status.value = try {
            player.value?.let {
                val res = FirebaseDBManager.createPlayer(it)
                // Only upload image if creating player was successful
                if(res){
                    FirebaseImageManager.updatePlayerImage(it)
                }
                res
            } ?: run{
                false
            }
        } catch (e: IllegalArgumentException) {
            false
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