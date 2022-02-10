package org.mk.basketballmanager.models.datastores

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.mk.basketballmanager.models.PlayerModel
import org.mk.playlist.helpers.exists
import org.mk.playlist.helpers.read
import org.mk.playlist.helpers.write
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.HashMap

private const val PLAYERS_JSON_FILE = "playlist.json"

class PlayerStore(private val context: Context) : DataStore<PlayerModel> {
    private val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting().create()
    private val listType: Type = object : TypeToken<HashMap<UUID , PlayerModel>>() {}.type
    private var players: HashMap<UUID, PlayerModel> = HashMap()
    
    init {
        if (exists(context, PLAYERS_JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): ArrayList<PlayerModel> {
        return ArrayList(players.values)
    }
    override fun create(obj: PlayerModel){

    }
    override fun update(obj: PlayerModel){
        val playlistToUpdate = players[obj.id]
        // If it finds the correct track by id, update it.
        playlistToUpdate?.let{
            players[obj.id] = obj
            serialize()
        }
    }
    override fun add(obj: PlayerModel){
        players[obj.id] = obj
        serialize()
    }

    override fun delete(obj: PlayerModel) {
        players.remove(obj.id)
        serialize()
    }



    private fun serialize() {
        val jsonString = gsonBuilder.toJson(players, listType)
        write(context, PLAYERS_JSON_FILE, jsonString)
    }
    private fun deserialize() {
        val jsonString = read(context, PLAYERS_JSON_FILE)
        players = gsonBuilder.fromJson(jsonString, listType)
    }
}