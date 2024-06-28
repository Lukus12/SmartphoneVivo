package com.example.smartphonevivo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.net.Uri
import android.view.View
import android.view.WindowManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView

class Player : AppCompatActivity() {
    private lateinit var playerView: PlayerView
    private lateinit var exoPlayer: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        playerView = findViewById(R.id.player_view)

        exoPlayer = ExoPlayer.Builder(this).build()
        playerView.player = exoPlayer

        val videoUrl = intent.getStringExtra("URL")
        if (videoUrl != null) {
            val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        }

        playerView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )

    }

    override fun onStop() {
        super.onStop()
        exoPlayer.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}