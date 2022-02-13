package org.mk.basketballmanager.models.datastores

import android.content.Context
import android.icu.text.Transliterator
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.mk.basketballmanager.enums.Position
import org.mk.basketballmanager.models.TeamModel
import org.mk.basketballmanager.helpers.exists
import org.mk.basketballmanager.helpers.read
import org.mk.basketballmanager.helpers.write
import org.mk.basketballmanager.models.PlayerModel
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

    fun addUpdatePlayer(obj: TeamModel, player: PlayerModel, position: Position){
        val team = teams[obj.id]
        team?.let {
            it.roster[player.id] = position
        }
        serialize()
    }

    fun removePlayer(obj: TeamModel, player: PlayerModel){
        val team = teams[obj.id]
        team?.let {
            it.roster.remove(player.id)
        }
        serialize()
    }
    fun getRoster(obj: TeamModel): HashMap<UUID, Position>{
        teams[obj.id]?.let {
            return it.roster
        }
        return HashMap()
    }
    fun getTeamByUsername(username: String): TeamModel?{
        // Will find only one team with the given username
        return teams.values.findLast { team -> team.owner == username }
    }
    fun deletePlayerFromRosters(player: PlayerModel){
        for(team in teams.values){
            team.roster.remove(player.id)
        }
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