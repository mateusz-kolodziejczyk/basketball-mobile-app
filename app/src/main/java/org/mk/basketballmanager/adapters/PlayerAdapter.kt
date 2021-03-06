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

class PlayerAdapter(private var players: ArrayList<PlayerModel>,
                    private val onClickFunction: (PlayerModel) -> Unit
) :
    RecyclerView.Adapter<PlayerAdapter.MainHolder>(), Filterable {
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
    fun removeAt(position: Int) {
        players.removeAt(position)
        notifyItemRemoved(position)
    }
    override fun getItemCount(): Int = players.size

    // Filter Code taken from https://stackoverflow.com/a/37735562
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                players = results.values as ArrayList<PlayerModel>
                notifyDataSetChanged()
            }

            override fun performFiltering(constraint: CharSequence): FilterResults {
                var filteredResults: List<PlayerModel?>? = null
                filteredResults = if (constraint.isEmpty()) {
                    originalList
                } else {
                    getFilteredResults(constraint.toString()
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
            binding.root.tag = player
            binding.player = player

            binding.root.setOnClickListener { onClickFunction(player) }
            if(player.image.isNotEmpty()){
                Picasso.get()
                    .load(player.image)
                    .into(binding.image)
            }
            if(player.teamImage.isNotEmpty()){
                Picasso.get()
                    .load(player.teamImage)
                    .into(binding.teamImage)
            }
        }
    }

}
