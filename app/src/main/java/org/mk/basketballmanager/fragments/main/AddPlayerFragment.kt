package org.mk.basketballmanager.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import org.mk.basketballmanager.R
import org.mk.basketballmanager.activities.MainActivity
import org.mk.basketballmanager.app.MainApp
import org.mk.basketballmanager.databinding.FragmentAddUpdatePlayerBinding
import org.mk.basketballmanager.models.PlayerModel
import java.util.*

class AddPlayerFragment : Fragment() {
    lateinit var binding: FragmentAddUpdatePlayerBinding
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
        binding = FragmentAddUpdatePlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = activity as MainActivity

        val app = activity.application as MainApp
        // Set action bar title

        activity.setActionBarTitle("Add Player")

        binding.btnAdd.text = resources.getString(R.string.add)
        binding.btnAdd.setOnClickListener { currentView ->
                val newPlayer = PlayerModel(
                    id = UUID.randomUUID(),
                    name = binding.name.text.toString()
                )
                if(newPlayer.name.isEmpty()){
                    Snackbar.make(currentView, R.string.error_no_name, Snackbar.LENGTH_LONG)
                        .show()
                }
                else{
                    app.players.add(newPlayer)
                    navigateToRoster()
                }
            }
    }
    private fun navigateToRoster(){
        val action = AddPlayerFragmentDirections.actionAddPlayerFragmentToRosterFragment()
        NavHostFragment.findNavController(this).navigate(action)
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