package com.grigorevmp.musicplayer

interface Playable {
    fun onTrackPrevious()
    fun onTrackPlay()
    fun onTrackChange()
    fun onTrackPause()
    fun onTrackNext()
    fun onTrackRepeat()
    fun onTrackRandom()
}