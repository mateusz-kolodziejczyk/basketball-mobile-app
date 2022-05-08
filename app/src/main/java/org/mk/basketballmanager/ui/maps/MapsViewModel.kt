package org.mk.basketballmanager.ui.maps

import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap

class MapsViewModel : ViewModel() {

    lateinit var map : GoogleMap
    lateinit var geocoder : Geocoder
    var currentLocation = MutableLiveData<Location>()

}