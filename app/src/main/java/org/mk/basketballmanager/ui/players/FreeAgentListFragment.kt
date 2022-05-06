package org.mk.basketballmanager.ui.players

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.mk.basketballmanager.R
import org.mk.basketballmanager.activities.MainActivity
import org.mk.basketballmanager.adapters.PlayerAdapter
import org.mk.basketballmanager.databinding.FragmentListBinding
import org.mk.basketballmanager.models.PlayerModel
import org.mk.basketballmanager.ui.auth.LoginFragment
import org.mk.basketballmanager.utils.createLoader
import org.mk.basketballmanager.utils.hideLoader
import org.mk.basketballmanager.utils.showLoader

class FreeAgentListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val freeAgentListViewModel: FreeAgentListViewModel by activityViewModels()
    lateinit var loader : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        loader = createLoader(requireActivity())

        showLoader(loader, resources.getString(R.string.loading_players))

        setSwipeRefresh()
        freeAgentListViewModel.observableFreeAgents.observe(viewLifecycleOwner, Observer { players ->
            players?.let {
                render(players as ArrayList<PlayerModel>)
                hideLoader(loader)
                checkSwipeRefresh()
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(view.context)

        val activity = activity as MainActivity

        // Set action bar title
        binding.recyclerView.layoutManager = layoutManager
    }

    // Render
    private fun render(playerList: ArrayList<PlayerModel>){
        binding.recyclerView.adapter = PlayerAdapter(playerList, navigateToPlayerInfo)
        if(playerList.isEmpty()){
            binding.recyclerView.visibility = View.GONE
            binding.textNotFound.visibility = View.VISIBLE
            binding.textNotFound.text = resources.getString(R.string.playersNotFound)
        }else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.textNotFound.visibility = View.GONE
        }
    }

    // Menu
    // Taken from https://stackoverflow.com/a/52018980
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        setupOptionsMenu(inflater, menu)
    }

    private fun setupOptionsMenu(inflater: MenuInflater, menu: Menu) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchView = SearchView(activity as Context)
        menu.findItem(R.id.action_search).apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val adapter = binding.recyclerView.adapter as PlayerAdapter
                adapter.filter.filter(newText)
                return false
            }
        })
        searchView.setOnClickListener { view -> }
    }

    private val navigateToPlayerInfo = { player: PlayerModel ->
        val action = PlayerListDirections.actionPlayerListToPlayerDetailFragment(player.id)
        findNavController().navigate(action)
    }
    private fun navigateToAddPlayer(){

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                navigateToAddPlayer()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        showLoader(loader, resources.getString(R.string.loading_players))
        freeAgentListViewModel.load()
    }
    private fun setSwipeRefresh() {
        binding.swiperefresh.setOnRefreshListener {
            binding.swiperefresh.isRefreshing = true
            showLoader(loader, resources.getString(R.string.loading_players))
            freeAgentListViewModel.load()
        }
    }

    private fun checkSwipeRefresh() {
        if (binding.swiperefresh.isRefreshing)
            binding.swiperefresh.isRefreshing = false
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}