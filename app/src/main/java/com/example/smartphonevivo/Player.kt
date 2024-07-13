package com.example.smartphonevivo

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import android.net.Uri
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.common.MediaItem
import androidx.media3.ui.PlayerView
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import android.content.pm.ActivityInfo
import android.view.View
class Player : AppCompatActivity() {
    private lateinit var playerView: PlayerView
    private lateinit var exoPlayer: ExoPlayer

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_player)
        playerView = findViewById(R.id.player_view)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE

        exoPlayer = ExoPlayer.Builder(this).build()
        playerView.player = exoPlayer

        val videoUrl = intent.getStringExtra("URL")
        if (videoUrl != null) {
            val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.play()
        }

        playerView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )

        playerView.controllerShowTimeoutMs = 3000
        playerView.hideController()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exoPlayer.stop()
                finish()
            }
        })

    }

    override fun onStop() {
        super.onStop()
        exoPlayer.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }
}
