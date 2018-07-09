package com.sa.baseproject.appview.videoview.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sa.baseproject.R
import com.sa.baseproject.appview.videoview.components.VideoPlayerComponent
import kotlinx.android.synthetic.main.activity_video_play.*

/**
 * @author rebeccafranks
 * @since 2017/07/13.
 */

class VideoPlayerActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)

        val videoUrl = "http://docs.evostream.com/sample_content/assets/bunny.mp4"
        lifecycle.addObserver(VideoPlayerComponent(applicationContext, playerView, videoUrl))

    }
}
