package org.mk.basketballmanager.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class PlayerModel(val id: UUID, var name: String): Parcelable
