package org.mk.basketballmanager.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import org.mk.basketballmanager.R
import org.mk.basketballmanager.databinding.FragmentTeamHomeBinding
import org.mk.basketballmanager.fragments.login.SignInFragment
import org.mk.basketballmanager.fragments.login.SignInFragmentDirections

class TeamHomeFragment : Fragment() {
    private lateinit var binding: FragmentTeamHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        binding.viewRosterButton.setOnClickListener {
            navigateToRoster()
        }
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