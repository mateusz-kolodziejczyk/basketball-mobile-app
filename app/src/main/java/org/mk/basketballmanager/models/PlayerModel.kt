package org.mk.basketballmanager.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.mk.basketballmanager.enums.Position
import java.util.*

@Parcelize
data class PlayerModel(val id: UUID, var name: String, var preferredPosition: Position = Position.None): Parcelable
