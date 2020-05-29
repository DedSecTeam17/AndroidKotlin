package com.example.btncounterapp.music_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.btncounterapp.R
import com.example.btncounterapp.music_app.adapters.ArtistAlbumListAdapter
import com.example.btncounterapp.music_app.adapters.ArtistSongListAdapter
import com.example.btncounterapp.music_app.repostory.ApiCalls.ServiceBuilder
import com.example.btncounterapp.music_app.repostory.Responses.models.ArtistResponseItem
import com.google.gson.Gson

class ArtistInfoActivity : AppCompatActivity() {


    lateinit var toolbar: Toolbar
    lateinit var artistAlbums: RecyclerView
    lateinit var artistTracks: RecyclerView

    lateinit var authorName: TextView
    lateinit var authorInfo: TextView


    //    adapters
    lateinit var albumsAdapter: ArtistAlbumListAdapter
    lateinit var songsAdapter: ArtistSongListAdapter
    lateinit var data: ArtistResponseItem

    lateinit var artistImage: ImageView


//    var data = mutableListOf<Artist>(
//        Artist(title = "Test", image_url = "url"),
//        Artist(title = "Test", image_url = "url"),
//        Artist(title = "Test", image_url = "url"),
//        Artist(title = "Test", image_url = "url"),
//        Artist(title = "Test", image_url = "url"),
//        Artist(title = "Test", image_url = "url")
//
//
//    )

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist_info)

        data = Gson().fromJson(intent.extras?.getString("data"), ArtistResponseItem::class.java)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        authorName = findViewById(R.id.author_name)
        authorInfo = findViewById(R.id.author_info)
        artistImage = findViewById(R.id.artist_image_preview)
        authorName.text = data.artist_name
        authorInfo.text = "${data.albums.size} Albums . ${data.tracks.size} Songs . 55:23"
        Glide.with(this)
            .load("${ServiceBuilder.baseUrl}${data.artist_image_url.get(0).url}")
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL) //3
            .transform(CenterCrop(), RoundedCorners(15))

            .into(artistImage)


//
        artistAlbums = findViewById(R.id.artist_albums)
        albumsAdapter = ArtistAlbumListAdapter(data = data.albums)

        artistAlbums.apply {
            layoutManager = LinearLayoutManager(
                applicationContext, LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = albumsAdapter
        }
        artistAlbums.setNestedScrollingEnabled(false)

//        song stuff
        artistTracks = findViewById(R.id.artist_songs)
        songsAdapter = ArtistSongListAdapter(data = data.tracks)

        artistTracks.apply {
            layoutManager = LinearLayoutManager(
                applicationContext
            )
            adapter = songsAdapter
        }
        artistTracks.setNestedScrollingEnabled(false)

    }
}
