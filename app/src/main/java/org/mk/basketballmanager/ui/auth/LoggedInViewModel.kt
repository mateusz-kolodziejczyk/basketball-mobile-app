package org.mk.basketballmanager.ui.auth

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import org.mk.basketballmanager.firebase.FirebaseAuthManager

class LoggedInViewModel(app: Application) : AndroidViewModel(app) {

    var firebaseAuthManager : FirebaseAuthManager = FirebaseAuthManager(app)
    var liveFirebaseUser : MutableLiveData<FirebaseUser> = firebaseAuthManager.liveFirebaseUser
    val loggedOut = MutableLiveData<Boolean>()

    fun logOut(context: Context){
        //firebaseAuthManager.logOut()
        AuthUI.getInstance()
            .signOut(context)
            .addOnCompleteListener {
            }.addOnSuccessListener {
                loggedOut.value = true
            }
    }
}