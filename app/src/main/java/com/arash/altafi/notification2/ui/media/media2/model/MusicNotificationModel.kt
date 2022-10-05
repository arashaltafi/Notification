package com.arash.altafi.notification2.ui.media.media2.model

import com.arash.altafi.notification2.App
import com.arash.altafi.notification2.R
import com.arash.altafi.notification2.ui.media.media2.MusicNotification

class MusicNotificationModel {

    companion object {
        fun create(
            songInfo: SongInfo, position: Int,
            max_position: Int,
            play: Boolean = false,
            looping:Boolean = false
        ) {
            val icon = if (play) {
                R.drawable.ic_baseline_play_arrow_24
            } else {
                R.drawable.ic_baseline_pause_24
            }

            MusicNotification.createNotification(
                App.applicationContext(),
                songInfo,
                icon,
                position,
                max_position,
                looping = looping
            )
        }
    }
}