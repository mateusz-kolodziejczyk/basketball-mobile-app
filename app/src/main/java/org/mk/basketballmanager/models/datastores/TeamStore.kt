package org.mk.basketballmanager.models.datastores

import android.content.Context
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
    override fun findAll(): List<TeamModel> {
        TODO("Not yet implemented")
    }

    override fun create(obj: TeamModel) {
        TODO("Not yet implemented")
    }

    override fun update(obj: TeamModel) {
        TODO("Not yet implemented")
    }

    override fun add(obj: TeamModel) {
        TODO("Not yet implemented")
    }

    override fun delete(obj: TeamModel) {
        TODO("Not yet implemented")
    }

    override fun findOne(obj: TeamModel): TeamModel? {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }
}