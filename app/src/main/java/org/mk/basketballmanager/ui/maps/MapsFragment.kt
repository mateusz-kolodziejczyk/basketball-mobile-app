package org.mk.basketballmanager.ui.maps

import android.app.AlertDialog
import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.mk.basketballmanager.R
import org.mk.basketballmanager.models.PlayerModel
import org.mk.basketballmanager.models.TeamModel
import org.mk.basketballmanager.ui.auth.LoggedInViewModel
import org.mk.basketballmanager.ui.playerdetail.PlayerDetailViewModel
import org.mk.basketballmanager.ui.players.RosterViewModel
import org.mk.basketballmanager.ui.team.TeamListViewModel
import org.mk.basketballmanager.ui.team.TeamViewModel
import org.mk.basketballmanager.utils.createLoader
import org.mk.basketballmanager.utils.getAddress
import org.mk.basketballmanager.utils.hideLoader
import org.mk.basketballmanager.utils.showLoader
import java.lang.Exception

class MapsFragment : Fragment() {

    private val mapsViewModel: MapsViewModel by activityViewModels()
    private val playerListViewModel: PlayerDetailViewModel by activityViewModels()
    private val rosterViewModel: RosterViewModel by activityViewModels()
    private val teamListViewModel: TeamListViewModel by activityViewModels()
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()
    private val teamViewModel: TeamViewModel by activityViewModels()
    lateinit var loader : AlertDialog

    private val callback = OnMapReadyCallback { googleMap ->
        mapsViewModel.map = googleMap
        mapsViewModel.map.clear()
        mapsViewModel.geocoder = Geocoder(context)
        var loc = LatLng(52.245696, -7.139102)

        // Attempt to geocode team, if succeeds set loc to it.
        teamViewModel.observableTeam.value?.let{
            val s = getAddress(it)

            var result:MutableList<Address> = ArrayList()
            // Handle any errors that might occur, do not add marker if errors occur.
            try{
                result = mapsViewModel.geocoder.getFromLocationName(s, 1)
            }
            catch (e: Exception){
                result.clear()
            }
            if(result.isNotEmpty()) {
                val address = result[0]
                loc = LatLng(address.latitude, address.longitude)
                mapsViewModel.map.addMarker(
                    MarkerOptions().position(loc)
                        .title(it.name)
                        .snippet(s)
                        .icon(
                            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
                )
            }

        }
        mapsViewModel.map.uiSettings.isZoomControlsEnabled = true
        mapsViewModel.map.uiSettings.isMyLocationButtonEnabled = true
        mapsViewModel.map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 14f))

        rosterViewModel.observableRoster.observe(viewLifecycleOwner, Observer { roster ->
            roster?.let{
                renderPlayers(roster as ArrayList<PlayerModel>)
                hideLoader(loader)
            }
        })

        teamListViewModel.observableTeams.observe(viewLifecycleOwner, Observer { roster ->
            roster?.let{
                //renderTeams(roster as ArrayList<TeamModel>)
                //hideLoader(loader)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loader = createLoader(requireActivity())


        return inflater.inflate(R.layout.maps_fragment, container, false)
    }

    private fun renderPlayers(playerList: ArrayList<PlayerModel>){
        if(playerList.isEmpty()){
            return
        }
        playerList.forEach{ player ->
            // Try to get a location for player using their address, if there isn't one, don't add it.
            val s = getAddress(player)
            var result:MutableList<Address> = ArrayList()
            // Handle any errors that might occur, do not add marker if errors occur.
            try{
                result = mapsViewModel.geocoder.getFromLocationName(s, 1)
            }
            catch (e: Exception){
                result.clear()
            }
            if(result.isNotEmpty()){
                val address = result[0]
                val longitude = address.longitude
                val latitude = address.latitude
                mapsViewModel.map.addMarker(
                    MarkerOptions().position(LatLng(latitude, longitude))
                        .title(player.name)
                        .snippet(s)
                        .icon(
                            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                )
            }
        }
    }

    private fun renderTeams(teamList: ArrayList<TeamModel>){
        if(teamList.isEmpty()){
            return
        }
        teamList.forEach{ team ->
            // Try to get a location for player using their address, if there isn't one, don't add it.
            val s = getAddress(team)
            // Get location
            val result = mapsViewModel.geocoder.getFromLocationName(s, 1)
            if(result.isNotEmpty()){
                val address = result[0]
                val longitude = address.longitude
                val latitude = address.latitude
                mapsViewModel.map.addMarker(
                    MarkerOptions().position(LatLng(latitude, longitude))
                        .title(team.name)
                        .snippet(s)
                        .icon(
                            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showLoader(loader, resources.getString(R.string.loading_players))
        teamListViewModel.load()
        teamViewModel.getTeam()
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                rosterViewModel.liveFirebaseUser.value = firebaseUser
                rosterViewModel.load()
            }
        })
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}