package org.mk.basketballmanager.models.datastores

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.mk.basketballmanager.models.TeamModel
import org.mk.basketballmanager.helpers.exists
import org.mk.basketballmanager.helpers.read
import org.mk.basketballmanager.helpers.write
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.HashMap

private const val TEAMS_JSON_FILE = "teams.json"

class TeamStore(private val context: Context) : DataStore<TeamModel> {
    private val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting().create()
    private val listType: Type = object : TypeToken<HashMap<UUID , TeamModel>>() {}.type
    private var teams: HashMap<UUID, TeamModel> = HashMap()
    
    init {
        if (exists(context, TEAMS_JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): ArrayList<TeamModel> {
        return ArrayList(teams.values)
    }

    override fun create(obj: TeamModel){

    }
    override fun update(obj: TeamModel){
        val playlistToUpdate = teams[obj.id]
        // If it finds the correct track by id, update it.
        playlistToUpdate?.let{
            teams[obj.id] = obj
            serialize()
        }
    }
    override fun add(obj: TeamModel){
        teams[obj.id] = obj
        serialize()
    }

    override fun delete(obj: TeamModel) {
        teams.remove(obj.id)
        serialize()
    }



    private fun serialize() {
        val jsonString = gsonBuilder.toJson(teams, listType)
        write(context, TEAMS_JSON_FILE, jsonString)
    }
    private fun deserialize() {
        val jsonString = read(context, TEAMS_JSON_FILE)
        teams = gsonBuilder.fromJson(jsonString, listType)
    }

    override fun findOne(obj: TeamModel): TeamModel? {
        return teams[obj.id]
    }

    override fun deleteAll() {
        teams = HashMap()
        serialize()
    }

}