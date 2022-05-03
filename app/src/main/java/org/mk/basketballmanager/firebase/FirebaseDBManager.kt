package ie.wit.donationx.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
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
        TODO("Not yet implemented")
    }

    override fun createTeam(firebaseUser: MutableLiveData<FirebaseUser>, team: TeamModel) {
        TODO("Not yet implemented")
    }

    override fun updateTeam(userID: String, team: TeamModel) {
        TODO("Not yet implemented")
    }

    override fun findAllPlayers(playersList: MutableLiveData<List<TeamModel>>) {
        TODO("Not yet implemented")
    }

    override fun findAllFreePlayers(playersList: MutableLiveData<List<PlayerModel>>) {
        TODO("Not yet implemented")
    }

    override fun findPlayerByID(userID: String, team: MutableLiveData<TeamModel>) {
        TODO("Not yet implemented")
    }

    override fun createPlayer(firebaseUser: MutableLiveData<FirebaseUser>, team: TeamModel) {
        TODO("Not yet implemented")
    }

    override fun updatePlayer(userID: String, team: PlayerModel) {
        TODO("Not yet implemented")
    }

    override fun getRoster(userID: String, roster: MutableLiveData<List<PlayerModel>>) {
        TODO("Not yet implemented")
    }

    override fun addPlayerToRoster(userID: String, team: TeamModel, playerID: String) {
        TODO("Not yet implemented")
    }

    override fun removePlayerFromRoster(userID: String, team: TeamModel, playerID: String) {
        TODO("Not yet implemented")
    }
}