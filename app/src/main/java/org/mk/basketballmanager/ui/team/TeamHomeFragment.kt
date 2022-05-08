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
import com.squareup.picasso.Picasso
import org.mk.basketballmanager.R
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
                binding.teamvm = teamViewModel
                if(it.image.isNotEmpty()){
                    Picasso.get()
                        .load(it.image)
                        .into(binding.image)
                }
                checkSwipeRefresh()
            }
        })
        setSwipeRefresh()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        showLoader(loader, resources.getString(R.string.loading_team))

        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                teamViewModel.liveFirebaseUser.value = firebaseUser
                teamViewModel.getTeam()
            }
        })
    }
    private fun setSwipeRefresh() {
        binding.swiperefresh.setOnRefreshListener {
            binding.swiperefresh.isRefreshing = true
            showLoader(loader, resources.getString(R.string.loading_team))
            teamViewModel.getTeam()
        }
    }

    private fun checkSwipeRefresh() {
        if (binding.swiperefresh.isRefreshing)
            binding.swiperefresh.isRefreshing = false
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TeamViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        loader = createLoader(requireActivity())
        loader.dismiss()
    }

}