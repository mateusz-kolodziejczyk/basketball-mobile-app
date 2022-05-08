package org.mk.basketballmanager.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import org.mk.basketballmanager.models.Addressable
import timber.log.Timber

const val REQUEST_PERMISSIONS_REQUEST_CODE = 34

fun checkLocationPermissions(activity: Activity) : Boolean {
    return if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        true
    }
    else {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSIONS_REQUEST_CODE)
        false
    }
}

fun isPermissionGranted(code: Int, grantResults: IntArray): Boolean {
    var permissionGranted = false;
    if (code == REQUEST_PERMISSIONS_REQUEST_CODE) {
        when {
            grantResults.isEmpty() -> Timber.i("User interaction was cancelled.")
            (grantResults[0] == PackageManager.PERMISSION_GRANTED) -> {
                permissionGranted = true
                Timber.i("Permission Granted.")
            }
            else -> Timber.i("Permission Denied.")
        }
    }
    return permissionGranted
}

fun getAddress(addressable: Addressable) : String{
    var s = ""
    val divider = ", "
    if(addressable.city.isNotEmpty()){
        s += addressable.city
        s += divider
    }
    if(addressable.region.isNotEmpty()){
        s += addressable.region
        s += divider
    }
    if(addressable.country.isNotEmpty()){
        s+= addressable.country
    }
    // Remove trailing ", "
    return s.removeSuffix(divider)
}