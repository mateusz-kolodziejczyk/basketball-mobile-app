package org.mk.basketballmanager.ui.players

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.mk.basketballmanager.R

class FreeAgentListFragment : Fragment() {

    companion object {
        fun newInstance() = FreeAgentListFragment()
    }

    private lateinit var viewModel: FreeAgentListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.free_agent_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FreeAgentListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}