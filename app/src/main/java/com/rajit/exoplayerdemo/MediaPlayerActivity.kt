package com.rajit.exoplayerdemo

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import com.rajit.exoplayerdemo.databinding.ActivityMediaPlayerBinding

class MediaPlayerActivity : AppCompatActivity() {

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMediaPlayerBinding.inflate(layoutInflater)
    }

    private var exoPlayer: ExoPlayer? = null
    private var fileUrl: String? = null

    private var playWhenReady = true
    private var mediaItemIndex = 0
    private var playbackPosition = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        fileUrl = intent.getStringExtra("url")

    }

    @OptIn(UnstableApi::class)
    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23 && fileUrl != null) {
            initializePlayer(fileUrl!!)
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
    }

    @OptIn(UnstableApi::class)
    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        exoPlayer?.let { player ->
            playbackPosition = player.currentPosition
            mediaItemIndex = player.currentMediaItemIndex
            playWhenReady = player.playWhenReady
            player.release()
        }
        exoPlayer = null
    }

    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, viewBinding.playerView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun initializePlayer(fileURL: String) {
        exoPlayer = ExoPlayer.Builder(this@MediaPlayerActivity)
            .build()
            .also { player ->
                viewBinding.playerView.player = player

                val mediaItem = MediaItem.fromUri(fileURL)
                player.setMediaItems(listOf(mediaItem), mediaItemIndex, playbackPosition)
                player.playWhenReady = playWhenReady
                player.prepare()

                player.addListener(object : Player.Listener {

                    override fun onPlayerError(error: PlaybackException) {
                        super.onPlayerError(error)
                        Toast.makeText(
                            this@MediaPlayerActivity,
                            "Error playing media. Error: $error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
    }

}