package org.mk.basketballmanager.models.datastores

import android.content.Context
import android.net.Uri
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.mk.basketballmanager.models.PlayerModel
import org.mk.basketballmanager.helpers.exists
import org.mk.basketballmanager.helpers.read
import org.mk.basketballmanager.helpers.write
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.HashMap

private const val PLAYERS_JSON_FILE = "players.json"

class PlayerStore(private val context: Context) : DataStore<PlayerModel> {
    override fun findAll(): List<PlayerModel> {
        TODO("Not yet implemented")
    }

    override fun create(obj: PlayerModel) {
        TODO("Not yet implemented")
    }

    override fun update(obj: PlayerModel) {
        TODO("Not yet implemented")
    }

    override fun add(obj: PlayerModel) {
        TODO("Not yet implemented")
    }

    override fun delete(obj: PlayerModel) {
        TODO("Not yet implemented")
    }

    override fun findOne(obj: PlayerModel): PlayerModel? {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }
}