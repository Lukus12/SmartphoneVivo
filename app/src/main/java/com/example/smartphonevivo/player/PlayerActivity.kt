package com.example.smartphonevivo.player

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.smartphonevivo.R
import com.squareup.picasso.Picasso

class PlayerActivity : AppCompatActivity() {
    private lateinit var playerView: PlayerView
    private lateinit var exoPlayer: ExoPlayer
    private var isBarAnimating = false
    private var isShowingTrackSelectionDialog = false

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val videoUrl = intent.getStringExtra("URL")
        val nameTV = intent.getStringExtra("NAME_TV")
        val imageURL = intent.getStringExtra("IMAGE_URL")

        playerView = findViewById(R.id.player_view)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE

        exoPlayer = ExoPlayer.Builder(this).build()
        playerView.player = exoPlayer
        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        WindowInsetsControllerCompat(window, window.decorView).let {
            it.hide(WindowInsetsCompat.Type.systemBars())
            it.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        playerView.hideController()
        playerView.controllerShowTimeoutMs = 3000

        val imageURLWidget: ImageView = playerView.findViewById(R.id.tv_image)
        Picasso.get().load(imageURL).into(imageURLWidget)

        val nameTVWidget: TextView = playerView.findViewById(R.id.tv_name)
        nameTVWidget.text = nameTV
        nameTVWidget.setShadowLayer(1.5f, 0f, 1f, Color.BLACK)

        //animating
        val topBar = playerView.findViewById<ConstraintLayout>(R.id.top_bar)
        val bottomBar = playerView.findViewById<ConstraintLayout>(R.id.bottom_bar)
        playerView.setOnClickListener {
            if (!isBarAnimating){
                if (playerView.isControllerFullyVisible) {
                    playerView.hideController()
                    animateHideBar(topBar)
                    animateHideBar(bottomBar)
                } else {
                    playerView.showController()
                    animateShowBar(topBar)
                    animateShowBar(bottomBar)
                }
            }
        }

        //backButton
        val backButtonWidget = playerView.findViewById<ImageButton>(R.id.back_button)
        backButtonWidget.setOnClickListener {
            exoPlayer.stop()
            finish()
        }

        //settingButton
        val settingsButtonWidget = playerView.findViewById<ImageButton>(R.id.setting_button)
        settingsButtonWidget.setOnClickListener {
            if (!isShowingTrackSelectionDialog && TrackSelectionDialog.willHaveContent(exoPlayer.currentTracks)) {
                isShowingTrackSelectionDialog = true
                val trackSelectionDialog = TrackSelectionDialog.createForPlayer(
                    exoPlayer
                ) { dismissedDialog: DialogInterface? ->
                    isShowingTrackSelectionDialog = false
                }
                trackSelectionDialog.show(supportFragmentManager, null)
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exoPlayer.stop()
                finish()
            }
        })
    }

    private fun animateShowBar(bar: ConstraintLayout) {
        if (bar.visibility == ConstraintLayout.GONE) {
            bar.visibility = ConstraintLayout.VISIBLE
        }
        bar.translationY = if (bar.id == R.id.top_bar) -bar.height.toFloat() else bar.height.toFloat()
        ObjectAnimator.ofFloat(bar, "translationY", 0f).apply {
            duration = 250
            interpolator = AccelerateDecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    isBarAnimating = true
                }

                override fun onAnimationEnd(animation: Animator) {
                    isBarAnimating = false
                }
            })
            start()
        }
    }

    private fun animateHideBar(bar: ConstraintLayout) {
        if (bar.visibility == ConstraintLayout.VISIBLE) {
            ObjectAnimator.ofFloat(bar, "translationY", if (bar.id == R.id.top_bar) -bar.height.toFloat() else bar.height.toFloat()).apply {
                duration = 250
                interpolator = AccelerateDecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        isBarAnimating = true
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        bar.visibility = ConstraintLayout.GONE
                        isBarAnimating = false
                    }
                })
                start()
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (!hasFocus) {
            exoPlayer.pause()
        } else {
            exoPlayer.play()
        }
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
