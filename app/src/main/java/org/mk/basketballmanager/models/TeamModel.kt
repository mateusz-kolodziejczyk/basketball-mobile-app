package org.mk.basketballmanager.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class TeamModel(
    override var id: String = "",
    var name: String = "Team Name",
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
            "name" to name,
            "city" to city,
            "region" to region,
            "country" to country,
        )
    }
}