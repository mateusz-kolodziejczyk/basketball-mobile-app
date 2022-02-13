package org.mk.basketballmanager.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navGraphViewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.mk.basketballmanager.R
import org.mk.basketballmanager.activities.MainActivity
import org.mk.basketballmanager.app.MainApp
import org.mk.basketballmanager.databinding.FragmentTeamHomeBinding
import org.mk.basketballmanager.fragments.login.SignInFragment
import org.mk.basketballmanager.models.TeamModel
import org.mk.basketballmanager.viewmodels.TeamViewModel
import java.math.RoundingMode
import java.util.*

private const val ARG_USERNAME = "username"

class TeamHomeFragment : Fragment() {
    private var username: String? = null
    private lateinit var binding: FragmentTeamHomeBinding
    private val model: TeamViewModel by navGraphViewModels(R.id.main_navigation)

    // Handle the showing of the google map inside the fragment
    private val callback = OnMapReadyCallback { googleMap ->
        var position = LatLng(25.0, 151.0)
        var zoom = googleMap.cameraPosition.zoom
        model.getSelectedTeam().observe(viewLifecycleOwner, { team ->
            val loc = team.location
            position = LatLng(loc.lat, loc.lng)
            zoom = loc.zoom
        })
        val options = MarkerOptions()
            .title("Team Location")
            .snippet("GPS : ${position.latitude.toBigDecimal().setScale(3, RoundingMode.HALF_EVEN)}, ${position.longitude.toBigDecimal().setScale(3, RoundingMode.HALF_EVEN)} ")
            .draggable(false)
            .position(position)
        googleMap.addMarker(options)

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoom))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeamHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as MainActivity

        val app = activity.application as MainApp
        // Handle the map
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        // Set action bar title

        activity.setActionBarTitle("Team Home")
        // Create or retrieve team based on username
        username?.let{ user ->
            val foundTeam = app.teams.getTeamByUsername(user)
            // If a team was found, the team will be set to the found one
            // Otherwise create a new team for the user
            foundTeam?.let {
                model.selectTeam(it)
            } ?: run{
                val newTeam = TeamModel(
                    id=UUID.randomUUID(),
                    name="Team $user",
                    owner=user)
                app.teams.add(newTeam)
                model.selectTeam(newTeam)
            }
        }
        model.getSelectedTeam().observe(viewLifecycleOwner, { teamModel ->
            binding.name.text = teamModel.name
        })

    }

    fun navigateToRoster(){
        val action = TeamHomeFragmentDirections.actionTeamHomeFragmentToRosterFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignInFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}