package org.mk.basketballmanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.mk.basketballmanager.databinding.CardPlayerBinding
import org.mk.basketballmanager.models.PlayerModel
import java.util.*
import kotlin.collections.ArrayList

class RosterAdapter(private var players: List<PlayerModel>,
                    private val onClickFunction: (PlayerModel) -> Unit
) :
    RecyclerView.Adapter<RosterAdapter.MainHolder>(), Filterable {
    val originalList = players
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardPlayerBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val playlist = players[holder.adapterPosition]
        holder.bind(playlist, onClickFunction)
    }

    override fun getItemCount(): Int = players.size
    // Filter Code taken from https://stackoverflow.com/a/37735562
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                players = results.values as List<PlayerModel>
                notifyDataSetChanged()
            }

            override fun performFiltering(constraint: CharSequence): FilterResults {
                var filteredResults: List<PlayerModel?>? = null
                if (constraint.isEmpty()) {
                    filteredResults = originalList
                } else {
                    filteredResults = getFilteredResults(constraint.toString()
                        .lowercase(Locale.getDefault()))
                }
                val results = FilterResults()
                results.values = filteredResults
                return results
            }
        }
    }
    private fun getFilteredResults(constraint: String): List<PlayerModel> {
        val results: MutableList<PlayerModel> = ArrayList()
        for (artist in originalList) {
            if (artist.name.lowercase(Locale.getDefault()).contains(constraint)) {
                results.add(artist)
            }
        }
        return results
    }

    inner class MainHolder(private val binding : CardPlayerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(player: PlayerModel, onClickFunction: (PlayerModel) -> Unit) {
            binding.name.text = player.name
            binding.root.setOnClickListener { onClickFunction(player) }
            Picasso.get()
                .load(player.image)
                .into(binding.image)
        }
    }

}
