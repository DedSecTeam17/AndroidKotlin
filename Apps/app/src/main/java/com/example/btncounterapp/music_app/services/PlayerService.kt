package com.example.btncounterapp.music_app.services

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.btncounterapp.R
import com.example.btncounterapp.music_app.repostory.ApiCalls.ServiceBuilder
import com.example.btncounterapp.music_app.repostory.Responses.models.TrackResponseItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.util.Util.getUserAgent


class PlayerService : Service() {

    var player: SimpleExoPlayer? = null
    lateinit var playerNotificationManager: PlayerNotificationManager
    var sound_uri = ""
    var title: String? = null
    var artist: String? = null
    var artist_image: String? = null
    var image: String? = null
    var position = 0
    var mediaMetaDataArrayList: ArrayList<TrackResponseItem> = ArrayList()
    private val mBinder = LocalBinder()


    inner class LocalBinder : Binder() {
        val service: PlayerService
            get() = this@PlayerService
    }


    fun getPlayerInstance(): SimpleExoPlayer? {
        if (player == null) {
            startPlayer()
        }
        return player;
    }


    override fun onDestroy() {
        super.onDestroy()
        // remove it when it not null
        player?.release()
        playerNotificationManager.setPlayer(null)


    }


    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            if (intent.action.equals("PLAY")) {
                sound_uri = intent.extras?.getString("url")!!
                artist = intent.extras?.getString("artist")!!
                artist_image = intent.extras?.getString("artist_image")!!
                image = intent.extras?.getString("image")!!
                title = intent.extras?.getString("title")!!
                position = intent.extras?.getInt("position", 0)!!
//                if (intent.getParcelableArrayListExtra("album") != null) {
//                    mediaMetaDataArrayList = intent.getParcelableArrayListExtra("album");
//
//
//                }
                startPlayer()
            }

        }
        return START_STICKY
    }


    fun getDominantColor(bitmap: Bitmap): Int {
        val newBitmap = Bitmap.createScaledBitmap(bitmap, 10, 10, true)
        val color = newBitmap.getPixel(0, 0)
        newBitmap.recycle()
        return color
    }

    private fun setupPlayer() {
        player = SimpleExoPlayer.Builder(this).build()

        val dataSourceFactory = DefaultDataSourceFactory(
            applicationContext,
            getUserAgent(applicationContext, "boraq")
        )
        val cacheDataSourceFactory =
            CacheDataSourceFactory(DownloadUtil.getCache(this), dataSourceFactory)
        if (mediaMetaDataArrayList.size > 0) {
            val concatenatingMediaSource = ConcatenatingMediaSource()


            for (mediaMetaData in mediaMetaDataArrayList) {
                val mediaSource = ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                    .createMediaSource(
                        Uri.parse(
                            "${ServiceBuilder.baseUrl}${mediaMetaData.track_sound_url.get(
                                0
                            ).url}"
                        )
                    )
                concatenatingMediaSource.addMediaSource(mediaSource)
            }

            player!!.prepare(concatenatingMediaSource)
            player!!.seekToDefaultPosition(position)
        } else {
            val uri = Uri.parse("${ServiceBuilder.baseUrl}$sound_uri")
            val audioResource = ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                .createMediaSource(uri)
            player!!.prepare(audioResource)
        }
    }

    private fun fireNotification(image: Bitmap) {

        if (mediaMetaDataArrayList.size > 0) {
            playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(
                applicationContext,
                "46",
                R.string.alhan_channel,
                R.string.alhan_description,
                12,
                DescriptionAdapter(image, applicationContext, mediaMetaDataArrayList)
            )
        } else {
            playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(
                applicationContext,
                "46",
                R.string.alhan_channel,
                R.string.alhan_description,
                12,
                DescriptionAdapter(
                    image,
                    title!!,
                    artist!!,
                    applicationContext,
                    mediaMetaDataArrayList
                )
            )
        }
        playerNotificationManager.setNotificationListener(object :
            PlayerNotificationManager.NotificationListener {
            override fun onNotificationStarted(notificationId: Int, notification: Notification) {
                startForeground(notificationId, notification)
            }

            override fun onNotificationCancelled(notificationId: Int) {


            }

            override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    stopForeground(true)
                } else {
                    stopSelf()
                }
            }
        })


        playerNotificationManager.setColorized(true)

        playerNotificationManager.setUseStopAction(true)
        playerNotificationManager.setUseChronometer(true)
        playerNotificationManager.setColor(getDominantColor(image))

        playerNotificationManager.setUseNavigationActions(false)
        playerNotificationManager.setPriority(NotificationCompat.PRIORITY_HIGH)
        player?.playWhenReady = true

        playerNotificationManager.setSmallIcon(R.drawable.ic_album_black_24dp)

        playerNotificationManager.setPlayer(player)
    }

    private fun startPlayer() {
        setupPlayer()
        Glide.with(applicationContext)
            .asBitmap()
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.album_img)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .override(160, 160)
            ).load("${ServiceBuilder.baseUrl}${image}").into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    fireNotification(resource)
                }

            })


    }


}