package org.mk.basketballmanager.models

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize
import org.mk.basketballmanager.enums.Position
import java.util.*
@IgnoreExtraProperties
@Parcelize
data class PlayerModel(
    val id: String = "",
    val teamID: String = "",
    var name: String = "Player",
    var position: Position = Position.None,
    var teamPosition: Position = Position.None,
    var image: String = "",
) : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "teamID" to teamID,
            "name" to name,
            "position" to position,
            "teamPosition" to teamPosition,
            "image" to image,
        )
    }
}