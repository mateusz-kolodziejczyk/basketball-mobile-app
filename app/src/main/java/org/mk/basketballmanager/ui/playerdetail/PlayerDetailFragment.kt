package org.mk.basketballmanager.ui.playerdetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.mk.basketballmanager.R

class PlayerDetailFragment : Fragment() {

    companion object {
        fun newInstance() = PlayerDetailFragment()
    }

    private lateinit var viewModel: PlayerDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.player_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlayerDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}