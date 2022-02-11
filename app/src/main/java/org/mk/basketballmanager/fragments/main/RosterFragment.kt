package org.mk.basketballmanager.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.mk.basketballmanager.R
import org.mk.basketballmanager.adapters.RosterAdapter
import org.mk.basketballmanager.app.MainApp
import org.mk.basketballmanager.databinding.FragmentListBinding
import org.mk.basketballmanager.databinding.FragmentSignInBinding
import org.mk.basketballmanager.fragments.login.SignInFragment
import org.mk.basketballmanager.fragments.login.SignInFragmentDirections
import org.mk.basketballmanager.models.PlayerModel

class RosterFragment : Fragment() {
    private lateinit var binding: FragmentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(view.context)
        val app = activity?.application as MainApp

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = RosterAdapter(app.players.findAll(), navigateToPlayer)
    }

    private val navigateToPlayer = { player: PlayerModel ->
        val action = RosterFragmentDirections.actionRosterFragmentToUpdatePlayerFragment(player)
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