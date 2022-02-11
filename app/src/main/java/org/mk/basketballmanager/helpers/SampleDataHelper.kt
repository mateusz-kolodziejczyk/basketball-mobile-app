package org.mk.basketballmanager.helpers

import org.mk.basketballmanager.models.PlayerModel
import org.mk.basketballmanager.models.datastores.PlayerStore
import org.mk.basketballmanager.models.datastores.TeamStore
import java.util.*

fun addSampleData(playersStore: PlayerStore){
    playersStore.add(PlayerModel(UUID.randomUUID(), "Michael Jordan"))
    playersStore.add(PlayerModel(UUID.randomUUID(), "Lebron James"))
    playersStore.add(PlayerModel(UUID.randomUUID(), "Stephen Curry"))
}