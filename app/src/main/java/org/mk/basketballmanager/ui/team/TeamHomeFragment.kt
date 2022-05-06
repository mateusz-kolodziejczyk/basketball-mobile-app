package org.mk.basketballmanager.ui.team

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import org.mk.basketballmanager.ui.auth.LoggedInViewModel
import org.mk.basketballmanager.databinding.TeamHomeFragmentBinding
import org.mk.basketballmanager.utils.createLoader
import org.mk.basketballmanager.utils.hideLoader
import org.mk.basketballmanager.utils.showLoader

class TeamHomeFragment : Fragment() {
    lateinit var loader : AlertDialog
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private val teamViewModel : TeamViewModel by activityViewModels()
    private lateinit var binding : TeamHomeFragmentBinding
    companion object {
        fun newInstance() = TeamHomeFragment()
    }

    private lateinit var viewModel: TeamViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TeamHomeFragmentBinding.inflate(inflater, container, false)
        loader = createLoader(requireActivity())
        teamViewModel.observableTeam.observe(viewLifecycleOwner, Observer { team ->
            team?.let {
                hideLoader(loader)
                binding.name.text = it.name
            }
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        showLoader(loader, "Downloading Donations")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                teamViewModel.liveFirebaseUser.value = firebaseUser
                teamViewModel.getTeam()
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TeamViewModel::class.java)
        // TODO: Use the ViewModel
    }

}