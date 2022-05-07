package org.mk.basketballmanager.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import org.mk.basketballmanager.models.PlayerModel
import org.mk.basketballmanager.models.TeamModel
import org.mk.basketballmanager.models.datastores.BasketballManagerStore
import timber.log.Timber

object FirebaseDBManager : BasketballManagerStore {

    var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    override fun findAllTeams(teamsList: MutableLiveData<List<TeamModel>>) {
        TODO("Not yet implemented")
    }

    override fun findTeamByID(userID: String, team: MutableLiveData<TeamModel>) {
        database.child("teams").child(userID).get().addOnSuccessListener {
            team.value = it.getValue(TeamModel::class.java)
            Timber.i("firebase Got value ${it.value}")

            // If null, create the team
            if (team.value == null) {
                val teamModel = TeamModel(userID = userID)
                createTeam(userID, teamModel)
                team.value = teamModel
            }
        }.addOnFailureListener {
            Timber.e("firebase Error getting data $it")
        }
    }


    override fun createTeam(userID: String, team: TeamModel) {
        Timber.i("Firebase DB Reference : $database")
        team.userID = userID
        val teamValues = team.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/teams/$userID"] = teamValues

        database.updateChildren(childAdd)
    }

    override fun updateTeam(userID: String, team: TeamModel) {
        val teamValues = team.toMap()

        val childUpdate: MutableMap<String, Any?> = HashMap()
        childUpdate["/teams/$userID"] = teamValues

        database.updateChildren(childUpdate)
    }

    override fun findAllPlayers(playersList: MutableLiveData<List<PlayerModel>>) {
        // Query all players and add them to the mutablelist
        database.child("players").get().addOnSuccessListener { snapshot ->
            val localList = ArrayList<PlayerModel>()
            val children = snapshot.children
            children.forEach { childSnapshot ->
                val player = childSnapshot.getValue(PlayerModel::class.java)
                player?.let{
                    localList.add(player)
                }

            }
            playersList.value = localList
        }.addOnFailureListener{
            Timber.e("firebase Error getting data $it")
        }
    }

    override fun findAllFreePlayers(playersList: MutableLiveData<List<PlayerModel>>) {
        // Query all players but only add players without a teamid to the mutable list
        database.child("players").get().addOnSuccessListener { snapshot ->
            val localList = ArrayList<PlayerModel>()
            val children = snapshot.children
            children.forEach { childSnapshot ->
                val player = childSnapshot.getValue(PlayerModel::class.java)
                player?.let{
                    if(it.teamID.isEmpty()){
                        localList.add(player)
                    }
                }

            }
            playersList.value = localList
        }.addOnFailureListener {
            Timber.e("firebase Error getting data $it")
        }
    }

    override fun findPlayerByID(id: String, player: MutableLiveData<PlayerModel>) {
        database.child("players").child(id).get().addOnSuccessListener {
            player.value = it.getValue(PlayerModel::class.java)
            Timber.i("firebase Got value ${it.value}")
        }.addOnFailureListener{
            Timber.e("firebase Error getting data $it")
        }
    }

    override fun createPlayer(player: PlayerModel): Boolean {
        val key = getPlayerKey()
        if (key == null) {
                Timber.i("Firebase Error : Key Empty")
                return false
        }
        else{
            player.id = key
        }
        val values = player.toMap()
        val childAdd = HashMap<String, Any>()
        childAdd["/players/${player.id}"] = values
        database.updateChildren(childAdd)
        return true
    }
    fun getPlayerKey(): String? {
        return database.child("players").push().key
    }
    override fun updatePlayer(player: PlayerModel) {
        val values = player.toMap()

        val childUpdate: MutableMap<String, Any?> = HashMap()
        childUpdate["/players/${player.id}"] = values
        if (player.teamID.isNotEmpty()) {
            childUpdate["rosters/${player.teamID}/${player.id}"] = values
        }
        database.updateChildren(childUpdate)

    }

    override fun deletePlayer(id: String) {
        // Only allow deletion of free agents
        // As multiple users can use the app, first make sure the existing player has no team id
        // Then on success delete the player
        database.child("players").child(id).get().addOnSuccessListener { snapshot ->
            val player = snapshot.getValue(PlayerModel::class.java)
            player?.let {
                if(it.teamID.isNotEmpty()){
                    Timber.e("Cannot delete player as the player is in a team")
                    return@addOnSuccessListener
                }
                val childDelete: MutableMap<String, Any?> = HashMap()
                childDelete["/players/$id"] = null
                database.updateChildren(childDelete)
            }

        }.addOnFailureListener{
            Timber.e("firebase Error getting data $it")
        }

    }

    override fun getRoster(userID: String, roster: MutableLiveData<List<PlayerModel>>) {
        // Get all players from the userid roster.
        database.child("rosters").child(userID).get().addOnSuccessListener { snapshot ->
            val localList = ArrayList<PlayerModel>()
            val children = snapshot.children
            children.forEach {
                val player = it.getValue(PlayerModel::class.java)
                localList.add(player!!)
            }
            roster.value = localList
        }
    }

    override fun addPlayerToRoster(userID: String, player: PlayerModel) {
        // Add userid to player object
        player.teamID = userID
        val values = player.toMap()

        val childUpdate: MutableMap<String, Any?> = HashMap()
        childUpdate["/players/${player.id}"] = values
        childUpdate["/rosters/$userID/${player.id}"] = values

        database.updateChildren(childUpdate)

    }

    override fun removePlayerFromRoster(userID: String, player: PlayerModel) {
        // Remove userid from player
        player.teamID = ""
        val values = player.toMap()

        val childUpdate: MutableMap<String, Any?> = HashMap()
        childUpdate["/players/${player.id}"] = values
        childUpdate["/rosters/$userID/${player.id}"] = null

        database.updateChildren(childUpdate)

    }


    fun updatePlayerImage(player: PlayerModel, imageUri: String) {
        database.child("players").child(player.id).get().addOnSuccessListener { playerSnapshot ->
            playerSnapshot.child("image").ref.setValue(imageUri)
            if(player.teamID.isEmpty()){
                return@addOnSuccessListener
            }
            database.child("rosters").child(player.teamID).child(player.id).get().addOnSuccessListener { rosterSnapshot ->
                rosterSnapshot.child("image").ref.setValue(imageUri)
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
        }.addOnFailureListener{
            Timber.e("firebase Error getting data $it")
        }
    }

    fun updateRosterImage(player: PlayerModel, imageUri: String) {
        database.child("roster").child(player.teamID).child(player.id).get().addOnSuccessListener {

        }.addOnFailureListener{
            Timber.e("firebase Error getting data $it")
        }
    }
}