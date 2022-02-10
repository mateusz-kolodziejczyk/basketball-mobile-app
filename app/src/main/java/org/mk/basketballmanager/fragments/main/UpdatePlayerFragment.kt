package org.mk.basketballmanager.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.mk.basketballmanager.R
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
        return inflater.inflate(R.layout.fragment_add_update_player, container, false)
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