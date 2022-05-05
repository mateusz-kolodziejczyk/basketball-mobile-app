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
    var name: String = "",
) : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "userID" to userID,
            "name" to name,
        )
    }
}