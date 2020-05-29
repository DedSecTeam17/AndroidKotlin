package com.example.btncounterapp.music_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.btncounterapp.R
import com.example.btncounterapp.music_app.adapters.ArtistSongListAdapter
import com.example.btncounterapp.music_app.repostory.ApiCalls.ServiceBuilder
import com.example.btncounterapp.music_app.repostory.Responses.models.AlbumResponseItem
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumInfoActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar

    lateinit var authorImage: ImageView
    lateinit var albumName: TextView
    lateinit var authorInfo: TextView
    lateinit var albumImage: ImageView

    lateinit var songsRecyclerView: RecyclerView

    lateinit var songListAdapter: ArtistSongListAdapter

//    var songs = mutableListOf<Song>(
//        Song(songName = "Test", duration = "3:22"),
//        Song(songName = "Test", duration = "3:22"),
//        Song(songName = "Test", duration = "3:22"),
//        Song(songName = "Test", duration = "3:22"),
//        Song(songName = "Test", duration = "3:22"),
//        Song(songName = "Test", duration = "3:22"),
//        Song(songName = "Test", duration = "3:22")
//
//    )

    lateinit var albums: AlbumResponseItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_info2)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        authorImage = findViewById(R.id.author_image)
        albumName = findViewById(R.id.album_name)
        albumImage = findViewById(R.id.album_image_preview)

        getData(intent.extras?.getInt("id"))
    }

    fun getData(id: Int?) {
        ServiceBuilder.musicServiceProvider().getAlbumInfo(id = id)
            .enqueue(object : Callback<AlbumResponseItem> {
                override fun onFailure(call: Call<AlbumResponseItem>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG)
                        .show()
                }

                override fun onResponse(
                    call: Call<AlbumResponseItem>,
                    response: Response<AlbumResponseItem>
                ) {

                    albums = response.body()!!
                    Glide.with(applicationContext)
                        .load("${ServiceBuilder.baseUrl}${albums.artist.artist_image_url.get(0).formats?.small?.url}")
                        .thumbnail(0.1f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL) //3

                        .transform(CircleCrop())
                        .into(authorImage)
                    albumName.text = albums.album_name
                    authorInfo = findViewById(R.id.author_info)
                    authorInfo.text = "${albums.artist.artist_name} . 2020 . 44:55"
                    Glide.with(applicationContext)
                        .load("${ServiceBuilder.baseUrl}${albums.album_image_url.get(0).url}")
                        .thumbnail(0.1f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL) //3
                        .transform(CenterCrop(), RoundedCorners(15))

                        .into(albumImage)


//

                    songListAdapter = ArtistSongListAdapter(data = albums.tracks)

                    songsRecyclerView = findViewById(R.id.album_songs)
                    songsRecyclerView.apply {
                        layoutManager = LinearLayoutManager(applicationContext)
                        adapter = songListAdapter
                    }
                    songsRecyclerView.isNestedScrollingEnabled = false

                }
            })
    }
}
