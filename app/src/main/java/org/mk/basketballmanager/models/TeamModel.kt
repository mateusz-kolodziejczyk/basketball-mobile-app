package org.mk.basketballmanager.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize
import org.mk.basketballmanager.enums.Position
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet
@IgnoreExtraProperties
@Parcelize
data class TeamModel(
    var userID: String = "",
    var name: String = "Team Name",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    override var city: String = "",
    override var region: String = "",
    override var country: String = ""
) : Parcelable, Addressable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "userID" to userID,
            "name" to name,
            "latitude" to latitude,
            "longitude" to longitude,
            "city" to city,
            "region" to region,
            "country" to country,
        )
    }
}