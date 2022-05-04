package org.mk.basketballmanager.ui.teamhome

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.mk.basketballmanager.R

class TeamHomeFragment : Fragment() {

    companion object {
        fun newInstance() = TeamHomeFragment()
    }

    private lateinit var viewModel: TeamHomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.team_home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TeamHomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}