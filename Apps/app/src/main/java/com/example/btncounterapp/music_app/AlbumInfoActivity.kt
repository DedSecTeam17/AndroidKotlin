package com.example.btncounterapp.music_app

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.btncounterapp.R
import com.example.btncounterapp.music_app.adapters.ArtistSongListAdapter
import com.example.btncounterapp.music_app.adapters.SongListAdapter
import com.example.btncounterapp.music_app.models.Song

class AlbumInfoActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar

    lateinit var authorImage: ImageView
    lateinit var albumName: TextView
    lateinit var authorInfo: TextView
    lateinit var albumImage: ImageView

    lateinit var songsRecyclerView: RecyclerView

    lateinit var songListAdapter: ArtistSongListAdapter

    var songs = mutableListOf<Song>(
        Song(songName = "Test", duration = "3:22"),
        Song(songName = "Test", duration = "3:22"),
        Song(songName = "Test", duration = "3:22"),
        Song(songName = "Test", duration = "3:22"),
        Song(songName = "Test", duration = "3:22"),
        Song(songName = "Test", duration = "3:22"),
        Song(songName = "Test", duration = "3:22")

    )

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
        authorInfo = findViewById(R.id.author_info)
        albumImage = findViewById(R.id.album_image_preview)

        Glide.with(this)
            .load("https://images.unsplash.com/photo-1590615428797-ad80b4e5f554?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max")
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL) //3

            .transform(CircleCrop())
            .into(authorImage)

        songListAdapter = ArtistSongListAdapter(data = songs)

        songsRecyclerView = findViewById(R.id.album_songs)
        songsRecyclerView.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = songListAdapter
        }
        songsRecyclerView.isNestedScrollingEnabled = false


    }
}
