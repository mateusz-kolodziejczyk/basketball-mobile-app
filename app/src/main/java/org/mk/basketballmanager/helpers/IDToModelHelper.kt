package org.mk.playlist.helpers

import org.mk.playlist.models.TrackModel

fun trackIDsToTracks(ids: List<String>, tracks: List<TrackModel>): List<TrackModel>{
    // Return a list containing only elements with matching IDs
    return tracks.filter { track -> ids.contains(track.id) }
}