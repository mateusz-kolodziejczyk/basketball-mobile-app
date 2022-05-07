package org.mk.basketballmanager.models.datastores

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import org.mk.basketballmanager.models.PlayerModel
import org.mk.basketballmanager.models.TeamModel

interface BasketballManagerStore {
    // Teams
    fun findAllTeams(
        teamsList:
        MutableLiveData<List<TeamModel>>
    )

    fun findTeamByID(userID: String, team: MutableLiveData<TeamModel>)
    fun createTeam(userID: String, team: TeamModel)
    fun updateTeam(userID: String, team: TeamModel)

    // Players
    fun findAllPlayers(
        playersList:
        MutableLiveData<List<PlayerModel>>
    )

    fun findAllFreePlayers(playersList: MutableLiveData<List<PlayerModel>>)
    fun findPlayerByID(id: String, player: MutableLiveData<PlayerModel>)
    fun createPlayer(player: PlayerModel): Boolean
    fun updatePlayer(player: PlayerModel)
    fun deletePlayer(id: String)

    // Roster
    fun getRoster(userID: String, roster: MutableLiveData<List<PlayerModel>>)
    fun addPlayerToRoster(userID: String, player: PlayerModel)
    fun removePlayerFromRoster(userID: String, player: PlayerModel)
}