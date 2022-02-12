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
import org.mk.basketballmanager.databinding.FragmentUpdateTeamBinding
import org.mk.basketballmanager.models.Location
import org.mk.basketballmanager.models.PlayerModel
import org.mk.basketballmanager.models.TeamModel
import org.mk.basketballmanager.viewmodels.TeamViewModel

class UpdateTeamFragment : Fragment() {
    private val model: TeamViewModel by navGraphViewModels(R.id.main_navigation)

    lateinit var binding: FragmentUpdateTeamBinding
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
        binding = FragmentUpdateTeamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = activity as MainActivity

        val app = activity.application as MainApp
        // Set action bar title

        activity.setActionBarTitle("Update Team Info")
        binding.updateButton.text = resources.getString(R.string.update)
        model.getSelectedTeam().observe(viewLifecycleOwner, {  selectedTeam ->
            binding.updateLocationButton.setOnClickListener { _ ->
                navigateToMap()
            }
            binding.name.setText(selectedTeam.name)
            binding.updateButton.setOnClickListener { currentView ->
                val updatedTeam = TeamModel(
                    id = selectedTeam.id,
                    name = binding.name.text.toString(),
                    roster = selectedTeam.roster,
                    location = selectedTeam.location,
                )
                if(updatedTeam.name.isEmpty()){
                    Snackbar.make(currentView, R.string.error_no_name, Snackbar.LENGTH_LONG)
                        .show()
                }
                else{
                    app.teams.update(updatedTeam)
                    navigateToHome()
                }
            }
        })
    }
    private fun navigateToHome(){
        val action = UpdateTeamFragmentDirections.actionUpdateTeamFragmentToTeamHomeFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }
    private fun navigateToMap(){
        val action = UpdateTeamFragmentDirections.actionUpdateTeamFragmentToMapsFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }
    companion object {
        @JvmStatic
        fun newInstance(team: PlayerModel) =
            UpdatePlayerFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}