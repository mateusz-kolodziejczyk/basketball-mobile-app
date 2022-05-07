package org.mk.basketballmanager.ui.maps

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap

class MapsViewModel : ViewModel() {

    lateinit var map : GoogleMap
    var currentLocation = MutableLiveData<Location>()

}