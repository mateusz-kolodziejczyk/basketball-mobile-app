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
    override var id: String = "",
    var teamID: String = "",
    var name: String = "",
    var position: Position = Position.None,
    var image: String = "",
    override var city: String = "",
    override var region: String = "",
    override var country: String = ""

) : Parcelable, Addressable, Identifiable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "teamID" to teamID,
            "name" to name,
            "position" to position,
            "image" to image,
            "city" to city,
            "region" to region,
            "country" to country,
        )
    }


}