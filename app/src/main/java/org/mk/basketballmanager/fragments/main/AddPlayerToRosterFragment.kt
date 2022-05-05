package org.mk.basketballmanager.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.mk.basketballmanager.R
import org.mk.basketballmanager.activities.MainActivity
import org.mk.basketballmanager.adapters.SelectablePlayerAdapter
import org.mk.basketballmanager.app.MainApp
import org.mk.basketballmanager.databinding.FragmentAddPlayerToRosterBinding
import org.mk.basketballmanager.databinding.FragmentAddUpdatePlayerBinding
import org.mk.basketballmanager.enums.Position
import org.mk.basketballmanager.models.PlayerModel
import org.mk.basketballmanager.viewmodels.TeamViewModel
import java.util.*
import kotlin.collections.ArrayList

class AddPlayerToRosterFragment : Fragment() {
    private val model: TeamViewModel by navGraphViewModels(R.id.main_navigation)

    lateinit var binding: FragmentAddPlayerToRosterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddPlayerToRosterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = activity as MainActivity
        val layoutManager = LinearLayoutManager(view.context)

        val app = activity.application as MainApp
        binding.list.recyclerView.layoutManager = layoutManager
        val adapter = SelectablePlayerAdapter(
            ArrayList(

            )
        )
        binding.list.recyclerView.adapter = adapter
        // Set action bar title

        activity.setActionBarTitle("Add Player")
        binding.btnAdd.text = resources.getString(R.string.add)
        model.getSelectedTeam().observe(viewLifecycleOwner, { teamModel ->
            binding.btnAdd.setOnClickListener { currentView ->
                val playerToAdd = adapter.getSelectedPlayer()
                playerToAdd?.let { track ->
                    //app.teams.addUpdatePlayer(teamModel, playerToAdd, Position.None)
                    navigateToRoster()
                } ?: run {
                    Snackbar.make(currentView, R.string.error_no_player_selected, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    private fun navigateToRoster() {
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            UpdatePlayerFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}