package com.example.smartphonevivo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.net.Uri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView

class Player : AppCompatActivity() {
    private lateinit var playerView: PlayerView
    private lateinit var exoPlayer: ExoPlayer

    fun onStartPlayer(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player)

        playerView = findViewById(R.id.player_view)

        exoPlayer = ExoPlayer.Builder(this).build()
        playerView.player = exoPlayer


        val mediaItem =
            MediaItem.fromUri(Uri.parse("https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8"))
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    fun onStopPlayer() {
        super.onStop()
        exoPlayer.release()
    }
}