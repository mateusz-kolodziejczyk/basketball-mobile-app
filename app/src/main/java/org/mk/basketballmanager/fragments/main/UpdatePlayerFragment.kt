package org.mk.basketballmanager.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import org.mk.basketballmanager.R
import org.mk.basketballmanager.app.MainApp
import org.mk.basketballmanager.databinding.FragmentAddUpdatePlayerBinding
import org.mk.basketballmanager.models.PlayerModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PLAYER = "player"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdatePlayerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdatePlayerFragment : Fragment() {
    private var player: PlayerModel? = null
    lateinit var binding: FragmentAddUpdatePlayerBinding
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
        binding = FragmentAddUpdatePlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val app = activity?.application as MainApp
        binding.btnAdd.text = resources.getString(R.string.update)
        player?.let{
            binding.name.setText(it.name)
            binding.btnAdd.setOnClickListener { currentView ->
                val updatedPlayer = PlayerModel(
                    id = it.id,
                    name = binding.name.text.toString()
                )
                if(updatedPlayer.name.isEmpty()){
                    Snackbar.make(currentView, R.string.error_no_name, Snackbar.LENGTH_LONG)
                        .show()
                }
                else{
                    app.players.update(updatedPlayer)
                    navigateToRoster()
                }
            }
        }
    }
    private fun navigateToRoster(){
        val action = UpdatePlayerFragmentDirections.actionUpdatePlayerFragmentToRosterFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpdatePlayerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PLAYER, player)
                }
            }
    }
}