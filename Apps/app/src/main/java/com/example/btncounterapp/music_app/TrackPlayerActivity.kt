package com.example.btncounterapp.music_app

import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.btncounterapp.R
import com.example.btncounterapp.music_app.adapters.ArtistSongListAdapter
import com.example.btncounterapp.music_app.repostory.ApiCalls.ServiceBuilder
import com.example.btncounterapp.music_app.repostory.Responses.models.AlbumResponseItem
import com.example.btncounterapp.music_app.repostory.Responses.models.TrackResponseItem
import com.google.android.exoplayer2.ui.PlayerControlView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.ComponentName
import com.example.btncounterapp.music_app.services.PlayerService.LocalBinder
import android.os.IBinder
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.btncounterapp.music_app.services.PlayerService
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R.attr.name
import com.example.btncounterapp.MyApplication
import java.nio.file.Files.size
import android.app.ActivityManager
import android.content.Context.ACTIVITY_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Timeline
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R.attr.name
import android.content.Context
import android.content.Intent
import android.widget.TextView
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.Util


class TrackPlayerActivity : AppCompatActivity() {


    lateinit var albumImage: ImageView

    lateinit var playerCtrlView: PlayerControlView

    lateinit var mService: PlayerService
    var mBound: Boolean = false
    lateinit var player: SimpleExoPlayer
    var mIntent: Intent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_player)
        bindView()

        getData(intent.extras?.getInt("id"))
    }

    fun bindView() {
        albumImage = findViewById(R.id.album_image)
        playerCtrlView = findViewById(R.id.player)

    }

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            val binder = iBinder as PlayerService.LocalBinder
            mService = binder.service
            mBound = true
            initializePlayer()
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            mBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        if (isServiceRunning(PlayerService::class.java)) {
            stopService(Intent(this, PlayerService::class.java))
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(mConnection)
    }

    private fun initializePlayer() {
        if (mBound) {
            player = mService.getPlayerInstance()!!

//            //      we are in track
//            if (album.size() === 0) {
//
//                repeat()
//            }


            player.addListener(object : Player.EventListener {

                override fun onTimelineChanged(timeline: Timeline, reason: Int) {

                }

                override fun onPositionDiscontinuity(reason: Int) {

                    when (reason) {
                        Player.TIMELINE_CHANGE_REASON_DYNAMIC -> toNext(player.getCurrentWindowIndex())
                        Player.DISCONTINUITY_REASON_PERIOD_TRANSITION -> toNext(player.getCurrentWindowIndex())
                    }
                }
            })
            playerCtrlView.player = player


        }
    }

    private fun fireService() {
        if (isServiceRunning(PlayerService::class.java)) {
            stopService(mIntent)
            mIntent?.let { Util.startForegroundService(this, it) }
        } else {

            mIntent?.let { Util.startForegroundService(this, it) }
        }
    }

    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    private fun toNext(currentWindowIndex: Int) {
//        if (album != null) {
//            if (album.size() > 0) {
//                modalChannel = album.get(currentWindowIndex)
//
//                MyApplication.getSetApp().setCurrentTrack(modalChannel)
//                saveDataLocally()
//                setArtistInfo()
//            }
//        }
    }


    fun getData(id: Int?) {
        ServiceBuilder.musicServiceProvider().getTrackInfo(id = id)
            .enqueue(object : Callback<TrackResponseItem> {
                override fun onFailure(call: Call<TrackResponseItem>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG)
                        .show()
                }

                override fun onResponse(
                    call: Call<TrackResponseItem>,
                    response: Response<TrackResponseItem>
                ) {
                    loadAlbumImage(
                        url = "${ServiceBuilder.baseUrl}${response.body()?.album?.album_image_url?.get(
                            0
                        )?.url}"
                    )
                    startPlayer(response.body()!!)

                }
            })
    }

    private fun startPlayer(track: TrackResponseItem) {

        mIntent = Intent(this, PlayerService::class.java)

        mIntent!!.action = "PLAY"
        mIntent!!.putExtra("title", track.track_name)
        mIntent!!.putExtra("url", track.track_sound_url.get(0).url)

        mIntent!!.putExtra("position", 0)
        mIntent!!.putExtra("artist", track.artist.artist_name)
        mIntent!!.putExtra("image", track.album.album_image_url.get(0).formats.thumbnail.url)
        mIntent!!.putExtra("artist_image", track.artist.artist_image_url.get(0).formats.small.url)

        playerCtrlView.findViewById<TextView>(R.id.song_name).text = track.track_name
        playerCtrlView.findViewById<TextView>(R.id.song_author).text =  track.artist.artist_name



        bindService(mIntent, mConnection, Context.BIND_AUTO_CREATE)

        fireService()

    }


    private fun loadAlbumImage(url: String) {
        Glide.with(applicationContext)
            .load(
                url
            )
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL) //3
            .transform(CenterCrop(), RoundedCorners(15))
            .into(albumImage)
    }

}
