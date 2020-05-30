package com.example.btncounterapp.music_app.services

import android.content.Context
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.offline.DownloadManager
import com.google.android.exoplayer2.ui.DownloadNotificationHelper
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.util.Util.getUserAgent
import java.io.File


object DownloadUtil {


     private var cache: Cache?=null
    lateinit private var downloadManager: DownloadManager
    lateinit private var databaseProvider: ExoDatabaseProvider
    lateinit private var dataSourceFactory: DefaultHttpDataSourceFactory
    //      private val downloadNotificationHelper: DownloadNotificationHelper?
    val DOWNLOAD_NOTIFICATION_CHANNEL_ID = "download_channel"

    @Synchronized
    fun getCache(context: Context): Cache? {
        if (cache == null) {
            databaseProvider = ExoDatabaseProvider(context)
            val cacheDir = File(context.getExternalFilesDir(null), "downloads")
            cache = SimpleCache(cacheDir, NoOpCacheEvictor(), databaseProvider)
        }
        return cache
    }


    @Synchronized
    fun getDownloadManger(context: Context): DownloadManager {

        if (downloadManager == null) {
            if (databaseProvider == null) {
                databaseProvider = ExoDatabaseProvider(context)
            }
            dataSourceFactory = DefaultHttpDataSourceFactory(getUserAgent(context, "boraq"))

            val actionFile = File(context.getExternalFilesDir(null), "actions")

            downloadManager =
                DownloadManager(context, databaseProvider, getCache(context)!!, dataSourceFactory)
            downloadManager!!.setMaxParallelDownloads(4)

        }
        return downloadManager
    }


    fun getDownloadNotificationHelper(context: Context): DownloadNotificationHelper {

        return DownloadNotificationHelper(context, DOWNLOAD_NOTIFICATION_CHANNEL_ID)
    }


}