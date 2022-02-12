package org.mk.basketballmanager.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.mk.basketballmanager.enums.Position
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

@Parcelize
data class TeamModel(val id: UUID = UUID.randomUUID(), var name: String = "", var location: Location = Location(), var roster: HashMap<UUID, Position> = HashMap()):Parcelable