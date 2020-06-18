package com.example.android.moallem_challenge

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_video.*
import kotlinx.android.synthetic.main.custom_controller.*


class VideoActivity : AppCompatActivity() {

    private lateinit var videoUri: String
    private  lateinit var simpleExoPlayer: SimpleExoPlayer
    var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        window.decorView.setBackgroundColor(Color.WHITE)
        supportActionBar?.elevation = 0F
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        videoUri = intent.getStringExtra(VIDEO_URI_KEY)
        Toast.makeText(this, "Url: " + videoUri, Toast.LENGTH_LONG).show()
        val videoUrl: Uri = Uri.parse(videoUri+".mp4")



        val loadControl = DefaultLoadControl()
        val bandwidthMeter = DefaultBandwidthMeter()
        val trackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl)
        val factory = DefaultHttpDataSourceFactory(this.getString(R.string.app_name))
        val extractorsFactory = DefaultExtractorsFactory()
        val mediaSource = ExtractorMediaSource(videoUrl, factory, extractorsFactory, null, null)

        playerView.player = simpleExoPlayer
        playerView.keepScreenOn = true
        simpleExoPlayer?.prepare(mediaSource)
        simpleExoPlayer?.playWhenReady = true

        simpleExoPlayer?.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_BUFFERING) {
                    progress_bar.visibility = View.VISIBLE
                } else if (playbackState == Player.STATE_READY) {
                    progress_bar.visibility = View.GONE
                }
            }
        })

        bt_fullscreen.setOnClickListener {
            if (flag) {
                bt_fullscreen.setImageDrawable(
                    AppCompatResources.getDrawable(
                        this,
                        R.drawable.ic_fullscreen
                    )
                )
                this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                flag = false
            } else {
                bt_fullscreen.setImageDrawable(
                    AppCompatResources.getDrawable(
                        this,
                        R.drawable.ic_fullscreen_exit
                    )
                )
                this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                flag = true
            }
        }

    }
    override fun onPause() {
        super.onPause()
        simpleExoPlayer?.playWhenReady = false
        simpleExoPlayer?.playbackState
    }
    override fun onResume() {
        super.onResume()
        simpleExoPlayer?.playWhenReady = true
        simpleExoPlayer?.playbackState
    }
}