package org.mk.basketballmanager.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import org.mk.basketballmanager.R
import org.mk.basketballmanager.app.MainApp
import org.mk.basketballmanager.databinding.FragmentPlayerInfoBinding
import org.mk.basketballmanager.models.PlayerModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PLAYER = "player"
class PlayerInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var player: PlayerModel? = null
    private lateinit var binding: FragmentPlayerInfoBinding
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
        binding = FragmentPlayerInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val app = activity?.application as MainApp
        player?.let{ playerModel ->
            binding.name.text = playerModel.name
            binding.buttonDelete.setOnClickListener {
                app.players.delete(playerModel)
                navigateToRoster()
            }
            binding.buttonUpdate.setOnClickListener {
                navigateToUpdatePlayer(playerModel)
            }
        }


    }
    fun navigateToUpdatePlayer(player: PlayerModel){
            val action = PlayerInfoFragmentDirections.actionPlayerInfoFragmentToUpdatePlayerFragment(player)
            NavHostFragment.findNavController(this).navigate(action)
    }
    fun navigateToRoster(){
        val action = PlayerInfoFragmentDirections.actionPlayerInfoFragmentToRosterFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }
    companion object {
        @JvmStatic
        fun newInstance(player: PlayerModel) =
            PlayerInfoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PLAYER, player)
                }
            }
    }
}