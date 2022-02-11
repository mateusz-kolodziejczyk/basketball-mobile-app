package org.mk.basketballmanager.helpers

import org.mk.basketballmanager.models.PlayerModel
import java.util.*

fun playerIDsToModels(ids: List<UUID>, players: List<PlayerModel>): List<PlayerModel>{
    // Return a list containing only elements with matching IDs
    return players.filter { track -> ids.contains(track.id) }
}