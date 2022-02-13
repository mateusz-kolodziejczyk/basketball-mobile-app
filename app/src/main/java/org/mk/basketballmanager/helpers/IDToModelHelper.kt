package org.mk.basketballmanager.helpers

import org.mk.basketballmanager.models.PlayerModel
import org.mk.basketballmanager.models.TeamModel
import java.util.*
import kotlin.collections.HashSet

fun playerIDsToModels(ids: List<UUID>, players: List<PlayerModel>): List<PlayerModel>{
    // Return a list containing only elements with matching IDs
    return players.filter { p -> ids.contains(p.id) }
}

fun getPlayersWithoutTeam(teams: List<TeamModel>, players: List<PlayerModel>): List<PlayerModel>{
    val playerIDs = HashSet<UUID>()
    for (team in teams){
        playerIDs.addAll(team.roster.keys)
    }
    // Return a list containing players not found in any team
    return players.filter { p -> !playerIDs.contains(p.id) }
}