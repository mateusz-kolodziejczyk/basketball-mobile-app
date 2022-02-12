package org.mk.basketballmanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mk.basketballmanager.models.Location

class LocationViewModel : ViewModel() {
    private val location = MutableLiveData<Location>()
    fun setLocation(location: Location){
        this.location.value = location
    }
    fun getLocation(): LiveData<Location>{
        return location
    }
}