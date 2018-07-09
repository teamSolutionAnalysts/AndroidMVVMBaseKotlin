package com.sa.baseproject.appview.videoview.components

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil
import com.google.android.exoplayer2.source.BehindLiveWindowException
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.sa.baseproject.R

/**
 * A version of https://github.com/google/ExoPlayer/blob/release-v2/demo/src/main/java/com/google/android/exoplayer2/demo/PlayerActivity.java
 * That uses LifecycleObserver annotations instead of having the code all inside the activity.
 * NOTE: This is not a full implementation for ExoPlayer to play all different kinds of content (DRM session management etc).
 *
 *
 * This is purely to demonstrate the power of a LifecycleObserver and how it can remove a lot of code from your activities.
 * Please refer to the above URL for a more complete example that takes care of more erroneous situations.
 *
 * @author rebeccafranks
 * @since 2017/07/11.
 */

class VideoPlayerComponent(private val context: Context, private val simpleExoPlayerView: SimpleExoPlayerView, private val videoUrl: String) : LifecycleObserver, ExoPlayer.EventListener {
    private var resumeWindow: Int = 0
    private var resumePosition: Long = 0

    private var player: SimpleExoPlayer? = null
    private val bandwidthMeter = DefaultBandwidthMeter()
    private val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
    private var trackSelector: DefaultTrackSelector? = null

    init {
        clearResumePosition()
        simpleExoPlayerView.requestFocus()
        if (Util.SDK_INT <= 23) {
            initializePlayer()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    internal fun onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    internal fun onStart() {
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    internal fun onStop() {
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    internal fun onPause() {
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        if (player != null) {
            updateResumePosition()
            player!!.release()
            player = null
            trackSelector = null
        }
    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    internal fun onResume() {
//        if (Util.SDK_INT <= 23) {
//            initializePlayer()
//        }
//    }

    private fun initializePlayer() {
        if (player == null) {
            trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector!!)
            player!!.addListener(this)
            val dataSourceFactory = DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, "testApp"), bandwidthMeter)

            val extractorsFactory = DefaultExtractorsFactory()
            val videoSource = ExtractorMediaSource(Uri.parse(videoUrl),
                    dataSourceFactory, extractorsFactory, null, null)
            simpleExoPlayerView.player = player
            player!!.playWhenReady = true

            val haveResumePosition = resumeWindow != C.INDEX_UNSET
            if (haveResumePosition) {
                Log.d(TAG, "Have Resume position true!$resumePosition")
                player!!.seekTo(resumeWindow, resumePosition)
            }

            player!!.prepare(videoSource, !haveResumePosition, false)

        }
    }

    private fun updateResumePosition() {
        resumeWindow = player!!.currentWindowIndex
        resumePosition = if (player!!.isCurrentWindowSeekable)
            Math.max(0, player!!.currentPosition)
        else
            C.TIME_UNSET
    }

    private fun clearResumePosition() {
        resumeWindow = C.INDEX_UNSET
        resumePosition = C.TIME_UNSET
    }

    override fun onTimelineChanged(timeline: Timeline, manifest: Any?) {

    }

    override fun onTracksChanged(trackGroups: TrackGroupArray, trackSelections: TrackSelectionArray) {

    }

    override fun onLoadingChanged(isLoading: Boolean) {}

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {

    }

    override fun onPlayerError(e: ExoPlaybackException) {
        var errorString: String? = null
        if (e.type == ExoPlaybackException.TYPE_RENDERER) {
            val cause = e.rendererException
            if (cause is MediaCodecRenderer.DecoderInitializationException) {
                // Special case for decoder initialization failures.
                errorString = if (cause.decoderName == null) {
                    when {
                        cause.cause is MediaCodecUtil.DecoderQueryException -> context.getString(R.string.error_querying_decoders)
                        cause.secureDecoderRequired -> context.getString(R.string.error_no_secure_decoder,
                                cause.mimeType)
                        else -> context.getString(R.string.error_no_decoder,
                                cause.mimeType)
                    }
                } else {
                    context.getString(R.string.error_instantiating_decoder,
                            cause.decoderName)
                }
            }
        }
        if (errorString != null) {
            showToast(errorString)
        }
        if (isBehindLiveWindow(e)) {
            clearResumePosition()
            initializePlayer()
        } else {
            updateResumePosition()

        }
    }

    override fun onPositionDiscontinuity() {

    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {

    }

    private fun showToast(message: String) {
        Toast.makeText(context.applicationContext, message, Toast.LENGTH_LONG).show()
    }

    companion object {

        private val TAG = "VideoPlayerComponent"

        private fun isBehindLiveWindow(e: ExoPlaybackException): Boolean {
            if (e.type != ExoPlaybackException.TYPE_SOURCE) {
                return false
            }
            var cause: Throwable? = e.sourceException
            while (cause != null) {
                if (cause is BehindLiveWindowException) {
                    return true
                }
                cause = cause.cause
            }
            return false
        }
    }

}
