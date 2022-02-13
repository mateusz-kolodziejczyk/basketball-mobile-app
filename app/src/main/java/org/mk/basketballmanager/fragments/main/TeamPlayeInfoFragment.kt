package org.mk.basketballmanager.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navGraphViewModels
import com.google.android.material.snackbar.Snackbar
import org.mk.basketballmanager.R
import org.mk.basketballmanager.activities.MainActivity
import org.mk.basketballmanager.app.MainApp
import org.mk.basketballmanager.databinding.FragmentPlayerInfoBinding
import org.mk.basketballmanager.databinding.FragmentTeamPlayerInfoBinding
import org.mk.basketballmanager.models.PlayerModel
import org.mk.basketballmanager.viewmodels.TeamViewModel

private const val ARG_PLAYER = "player"
class TeamPlayerInfoFragment : Fragment() {
    private val model: TeamViewModel by navGraphViewModels(R.id.main_navigation)
    private var player: PlayerModel? = null
    private lateinit var binding: FragmentTeamPlayerInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            player = it.getParcelable(ARG_PLAYER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTeamPlayerInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as MainActivity

        val app = activity.application as MainApp
        // Set action bar title

        activity.setActionBarTitle("Player Info")
        player?.let{ playerModel ->
            binding.playerInfo.name.text = playerModel.name
            binding.teamPlayerInfoContent.buttonDelete.setOnClickListener { currentView ->
                model.getSelectedTeam().value?.let { teamModel ->
                    app.teams.removePlayer(teamModel, playerModel)
                    navigateToRoster()
                } ?: run{
                    Snackbar.make(currentView, R.string.error_player_does_not_exist, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
            binding.playerInfo.buttonUpdate.setOnClickListener {
                navigateToUpdatePlayer(playerModel)
            }
        }


    }
    fun navigateToUpdatePlayer(player: PlayerModel){
            val action = TeamPlayerInfoFragmentDirections.actionPlayerInfoFragmentToUpdatePlayerFragment(player)
            NavHostFragment.findNavController(this).navigate(action)
    }
    fun navigateToRoster(){
        val action = TeamPlayerInfoFragmentDirections.actionPlayerInfoFragmentToRosterFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }
    companion object {
        @JvmStatic
        fun newInstance(player: PlayerModel) =
            TeamPlayerInfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PLAYER, player)
                }
            }
    }
}