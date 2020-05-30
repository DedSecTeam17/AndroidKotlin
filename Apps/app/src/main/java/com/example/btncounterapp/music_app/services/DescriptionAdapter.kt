package com.example.btncounterapp.music_app.services

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.annotation.Nullable
import com.example.btncounterapp.music_app.TrackPlayerActivity
import com.example.btncounterapp.music_app.repostory.Responses.models.TrackResponseItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import org.jetbrains.annotations.NotNull

 class DescriptionAdapter : PlayerNotificationManager.MediaDescriptionAdapter {
    internal var title: String? = null
    internal var author: String? = null
    internal var bitmap: Bitmap
    internal lateinit var applicationContext: Context
    internal lateinit var mediaMetaDataArrayList: ArrayList<TrackResponseItem>


    constructor(
        image: Bitmap,
        title: String,
        author: String,
        applicationContext: Context,
        mediaMetaDataArrayList: ArrayList<TrackResponseItem>
    ) {
        this.title = title
        this.author = author
        this.bitmap = image
        this.applicationContext = applicationContext
        this.mediaMetaDataArrayList = mediaMetaDataArrayList
    }

    constructor(
        image: Bitmap, applicationContext: Context,
        mediaMetaDataArrayList: ArrayList<TrackResponseItem>
    ) {
        this.title = null
        this.author = null
        this.bitmap = image
        this.bitmap = image
        this.applicationContext = applicationContext
        this.mediaMetaDataArrayList = mediaMetaDataArrayList

    }

    private val notificationPendingIntent: PendingIntent
        get() {

            val intent = Intent(applicationContext, TrackPlayerActivity::class.java)
            return PendingIntent.getActivity(
                applicationContext, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

    @NotNull
    override fun getCurrentContentTitle(player: Player): String {
        val window = player.currentWindowIndex
        return this.title!!

//        try {
//            return mediaMetaDataArrayList[window].track_name
//        } catch (e: Exception) {
//            return ""
//        }

    }

    @Nullable
    override fun createCurrentContentIntent(player: Player): PendingIntent? {

        return notificationPendingIntent
    }

    @Nullable
    override fun getCurrentContentText(player: Player): String? {
        val window = player.currentWindowIndex

        return this.author

//        try {
//            return mediaMetaDataArrayList[window].artist.artist_name
//        } catch (e: Exception) {
//            return ""
//        }


    }

    @Nullable
    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        val window = player.currentWindowIndex

        return bitmap
    }


}
