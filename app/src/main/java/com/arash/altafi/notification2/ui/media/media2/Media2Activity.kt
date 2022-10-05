package com.arash.altafi.notification2.ui.media.media2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arash.altafi.notification2.R
import com.arash.altafi.notification2.databinding.ActivityMedia2Binding
import com.arash.altafi.notification2.ui.media.media2.model.SongInfo
import com.arash.altafi.notification2.ui.media.media2.model.SongInfoModel
import com.arash.altafi.notification2.ui.media.media2.model.SongNames
import com.arash.altafi.notification2.ui.media.media2.service.OnClearFromRecentService
import com.arash.altafi.notification2.ui.media.media2.viewModel.MediaViewModel
import com.grigorevmp.musicplayer.Playable
import kotlin.random.Random

class Media2Activity : AppCompatActivity(), Playable {

    private lateinit var binding: ActivityMedia2Binding
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private lateinit var notificationManager: NotificationManager
    private val songs: MutableList<SongInfo> = SongInfoModel().loadSongs()
    private var position = 0
    private val songsNames: MutableList<String> = SongNames.data
    private val maxPosition = songsNames.size - 1
    private var mediaViewModel = MediaViewModel()
    private var isPlaying = false
    private var isLooping = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedia2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initMediaPlayer(position)
        createChannel()
        registerReceiver(
            broadCastReceiver,
            IntentFilter(
                "Songs action"
            )
        )
        startService(
            Intent(
                baseContext,
                OnClearFromRecentService::class.java
            )
        )

        binding.controlMusic.setOnClickListener {
            if (isPlaying) {
                onTrackPause()
            } else {
                onTrackPlay()
            }
        }

        mediaPlayer.setOnCompletionListener {
            position = (position + 1) % maxPosition
            onTrackChange()
        }
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    MusicNotification.CHANNEL_ID,
                    "Mini player",
                    NotificationManager.IMPORTANCE_HIGH
                )
            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    private fun initMediaPlayer(position: Int, change: Boolean = false) {
        if (change) {
            mediaPlayer.stop()
            mediaPlayer.reset()
        }

        val song = mediaViewModel.getSongByPosition(position)
        mediaPlayer.setDataSource(
            song.songDescriptor.fileDescriptor,
            song.songDescriptor.startOffset,
            song.songDescriptor.length
        )
        song.songDescriptor.close()

        mediaPlayer.prepare()
        mediaPlayer.setVolume(1f, 1f)
        mediaPlayer.isLooping = isLooping
    }

    private fun changeSongLooping() {
        isLooping = !mediaPlayer.isLooping
        mediaPlayer.isLooping = isLooping
    }

    private val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            when (intent?.extras?.getString("action_name")) {
                MusicNotification.ACTION_PREVIOUS -> onTrackPrevious()
                MusicNotification.ACTION_PLAY -> {
                    if (isPlaying)
                        onTrackPause()
                    else
                        onTrackPlay()
                }
                MusicNotification.ACTION_NEXT -> onTrackNext()
                MusicNotification.ACTION_REPEAT -> onTrackRepeat()
                MusicNotification.ACTION_RANDOM -> onTrackRandom()
            }
        }
    }

    override fun onTrackPrevious() {
        position -= 1
        initMediaPlayer(position, change = true)
        mediaPlayer.start()

        mediaViewModel.createMusicNotification(position, maxPosition, looping = isLooping)

    }

    override fun onTrackPlay() {
        isPlaying = true
        binding.controlMusic.setImageResource(R.drawable.ic_baseline_pause_24)
        mediaPlayer.start()

        mediaViewModel.createMusicNotification(position, maxPosition, looping = isLooping)
    }

    override fun onTrackChange() {
        initMediaPlayer(position, change = true)
        mediaPlayer.start()

        mediaViewModel.createMusicNotification(position, maxPosition, looping = isLooping)
    }

    override fun onTrackPause() {
        isPlaying = false
        binding.controlMusic.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        mediaPlayer.pause()

        mediaViewModel.createMusicNotification(position, maxPosition, true, looping = isLooping)
    }

    override fun onTrackRandom() {
        initMediaPlayer(position, change = true)
        mediaPlayer.start()

        val randomPosition = Random.nextInt(songs.size - 1)
        position = randomPosition

        mediaViewModel.createMusicNotification(position, maxPosition, looping = isLooping)
    }

    override fun onTrackNext() {
        position += 1
        initMediaPlayer(position, change = true)
        mediaPlayer.start()

        mediaViewModel.createMusicNotification(position, maxPosition, looping = isLooping)
    }

    override fun onTrackRepeat() {
        changeSongLooping()
        mediaViewModel.createMusicNotification(position, maxPosition, looping = isLooping)
    }

    override fun onDestroy() {
        super.onDestroy()
        notificationManager.cancelAll()
        unregisterReceiver(broadCastReceiver)
    }
}