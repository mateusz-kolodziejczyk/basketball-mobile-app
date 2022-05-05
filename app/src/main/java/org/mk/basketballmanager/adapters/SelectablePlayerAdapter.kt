package org.mk.basketballmanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.mk.basketballmanager.R
import org.mk.basketballmanager.databinding.CardPlayerBinding
import org.mk.basketballmanager.models.PlayerModel
import java.lang.IndexOutOfBoundsException
import java.util.*
import kotlin.collections.ArrayList

class SelectablePlayerAdapter(private var players: ArrayList<PlayerModel>,
): RecyclerView.Adapter<SelectablePlayerAdapter.MainHolder>() {
        private var selectedTrackID = UUID.randomUUID()
        var displayedPlayers: ArrayList<PlayerModel> = players

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
            val binding = CardPlayerBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return MainHolder(binding)
        }

        fun getSelectedPlayer() : PlayerModel?{
            return try{
               // displayedPlayers.filter { track -> track.id == selectedTrackID }[0]
                PlayerModel()
            } catch(e: IndexOutOfBoundsException){
                null
            }
        }

        override fun getItemCount(): Int {
            return displayedPlayers.size
        }

        override fun onBindViewHolder(holder: MainHolder, position: Int) {
            val player = displayedPlayers[holder.adapterPosition]
            //holder.bind(player, player.id == selectedTrackID)
        }
        inner class MainHolder(private val binding : CardPlayerBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(player: PlayerModel, isActivated: Boolean) {
                binding.root.isActivated = isActivated
                binding.name.text = player.name
                Picasso.get()
                    .load(player.image)
                    .into(binding.image)
                binding.position.text = player.position.toString()
                val context = binding.root.context
                // Change color if its activated, or not.
                if(isActivated){
                    binding.root.setCardBackgroundColor(ContextCompat.getColor(context, R.color.selected_background))
                }else{
                    binding.root.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
                }

                binding.root.setOnClickListener {
                    //selectedTrackID = player.id
                    notifyDataSetChanged()
                }
            }
        }
    }