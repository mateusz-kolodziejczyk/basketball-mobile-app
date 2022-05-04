package org.mk.basketballmanager.helpers

import androidx.core.net.toUri
import org.mk.basketballmanager.enums.Position
import org.mk.basketballmanager.models.PlayerModel
import org.mk.basketballmanager.models.datastores.PlayerStore
import java.util.*

fun addSampleData(playersStore: PlayerStore){
    playersStore.add(PlayerModel(UUID.randomUUID(), "Michael Jordan", position = Position.ShootingGuard, image = "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5c/Jordan_elgrafico_1992.jpg/800px-Jordan_elgrafico_1992.jpg".toUri()))
    playersStore.add(PlayerModel(UUID.randomUUID(), "Lebron James", position = Position.SmallForward, image = "https://upload.wikimedia.org/wikipedia/commons/c/cf/LeBron_James_crop.jpg".toUri()))
    playersStore.add(PlayerModel(UUID.randomUUID(), "Stephen Curry", position = Position.PointGuard, image = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/aa/TechCrunch_Disrupt_2019_%2848834853256%29_%281%29.jpg/800px-TechCrunch_Disrupt_2019_%2848834853256%29_%281%29.jpg".toUri()))
}