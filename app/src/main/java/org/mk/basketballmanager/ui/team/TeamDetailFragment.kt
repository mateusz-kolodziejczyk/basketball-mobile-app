package org.mk.basketballmanager.ui.team

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import ie.wit.donationx.ui.auth.LoggedInViewModel
import org.mk.basketballmanager.R
import org.mk.basketballmanager.activities.MainActivity
import org.mk.basketballmanager.app.MainApp
import org.mk.basketballmanager.databinding.FragmentUpdateTeamBinding
import org.mk.basketballmanager.databinding.TeamDetailFragmentBinding
import org.mk.basketballmanager.fragments.main.UpdatePlayerFragment
import org.mk.basketballmanager.models.PlayerModel
import org.mk.basketballmanager.viewmodels.LocationViewModel
import timber.log.Timber

class TeamDetailFragment : Fragment() {

    private val teamViewModel: TeamViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()

    lateinit var binding: TeamDetailFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = TeamDetailFragmentBinding.inflate(inflater, container, false)
        teamViewModel.observableTeam.observe(viewLifecycleOwner, Observer { render() })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = activity as MainActivity

        val app = activity.application as MainApp
        // Set action bar title

        activity.setActionBarTitle("Update Team Info")
        binding.updateButton.text = resources.getString(R.string.update)
    }

    override fun onResume() {
        super.onResume()
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                teamViewModel.liveFirebaseUser.value = firebaseUser
                teamViewModel.getTeam()
            }
        })
    }
    private fun navigateToHome(){
    }
    private fun navigateToMap(){
    }

    private fun render() {
        binding.teamvm = teamViewModel
        Timber.i("binding.teamvm == ${binding.teamvm}")
    }
}