package org.mk.basketballmanager.fragments.main

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.mk.basketballmanager.R
import org.mk.basketballmanager.activities.MainActivity
import org.mk.basketballmanager.app.MainApp
import org.mk.basketballmanager.viewmodels.LocationViewModel
import java.math.RoundingMode

class MapsFragment : Fragment() {
    private val model: LocationViewModel by navGraphViewModels(R.id.main_navigation)

    private val callback = OnMapReadyCallback { googleMap ->
        var position = LatLng(25.0, 151.0)
        var zoom = googleMap.cameraPosition.zoom
        model.getLocation().observe(viewLifecycleOwner, { loc ->
            position = LatLng(loc.lat, loc.lng)
            zoom = loc.zoom
        })
        val options = MarkerOptions()
            .title("Team Location")
            .snippet("GPS : ${position.latitude.toBigDecimal().setScale(3, RoundingMode.HALF_EVEN)}, ${position.longitude.toBigDecimal().setScale(3, RoundingMode.HALF_EVEN)} ")
            .draggable(true)
            .position(position)
        googleMap.addMarker(options)

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoom))

        googleMap.setOnMarkerDragListener(object: GoogleMap.OnMarkerDragListener {
            override fun onMarkerDrag(marker: Marker) {
            }

            override fun onMarkerDragEnd(marker: Marker) {
                val location = model.getLocation().value
                location?.let{
                    it.lat = marker.position.latitude
                    it.lng = marker.position.longitude
                    it.zoom = googleMap.cameraPosition.zoom
                }
            }

            override fun onMarkerDragStart(marker: Marker) {
            }
        })

        googleMap.setOnCameraIdleListener {
            val location = model.getLocation().value
            location?.let{
                it.zoom = googleMap.cameraPosition.zoom
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        val googleMap = mapFragment?.getMapAsync(callback)

        val activity = activity as MainActivity

        val app = activity.application as MainApp
        // Set action bar title

        activity.setActionBarTitle("Set Location")
    }
}