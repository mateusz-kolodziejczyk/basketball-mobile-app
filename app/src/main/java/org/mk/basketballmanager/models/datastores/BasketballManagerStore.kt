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
    fun createTeam(firebaseUser: MutableLiveData<FirebaseUser>, team: TeamModel)
    fun updateTeam(userID: String, team: TeamModel)

    // Players
    fun findAllPlayers(
        playersList:
        MutableLiveData<List<TeamModel>>
    )

    fun findAllFreePlayers(playersList: MutableLiveData<List<PlayerModel>>)
    fun findPlayerByID(userID: String, team: MutableLiveData<TeamModel>)
    fun createPlayer(firebaseUser: MutableLiveData<FirebaseUser>, team: TeamModel)
    fun updatePlayer(userID: String, team: PlayerModel)

    // Roster
    fun getRoster(userID: String, roster: MutableLiveData<List<PlayerModel>>)
    fun addPlayerToRoster(userID: String, team: TeamModel, playerID: String)
    fun removePlayerFromRoster(userID: String, team: TeamModel, playerID: String)
}