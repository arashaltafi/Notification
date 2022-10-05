package com.arash.altafi.notification2.ui.media.media2.model

import android.content.res.AssetFileDescriptor
import com.arash.altafi.notification2.App

class SongNames {
    companion object {
        val data: MutableList<String> = arrayListOf(
            "songs/song_1.mp3",
            "songs/song_2.mp3",
            "songs/song_3.mp3",
            "songs/song_4.mp3"
        )
    }
}

data class Song(
    val songName: String,
    val songDescriptor: AssetFileDescriptor
)

class SongModel {
    private val songsNames = SongNames.data
    fun loadSongById(position: Int): Song {
        return Song(
            songsNames[position],
            App.applicationContext().assets.openFd(songsNames[position])
        )
    }
}

