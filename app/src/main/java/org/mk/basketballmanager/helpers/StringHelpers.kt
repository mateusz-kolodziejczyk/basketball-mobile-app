package org.mk.playlist.helpers

import org.mk.playlist.models.ArtistModel

fun artistIDsToArtistString(artistIDs: LinkedHashSet<String>, artists: List<ArtistModel>) : String{
    // Create the string containing all the artist
    var s = ""
    // This is done using artist IDs stored in the track, as the track stores them in order of importance.
    artistIDs.forEach { artistID ->
        val artist = artists.find{it.id == artistID}
        if(artist != null){
            s += artist.name + ", "
        }
    }
    if(s.length >= 2){
        // This makes sure the last comma isn't included
        s = s.subSequence(0, s.length-2) as String
    }
    return s
}