package org.mk.basketballmanager.ui.playerdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mk.basketballmanager.firebase.FirebaseDBManager
import org.mk.basketballmanager.models.PlayerModel

class AddPlayerViewModel : ViewModel() {
    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addPlayer(player: PlayerModel) {
        status.value = try {
            FirebaseDBManager.createPlayer(player)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}