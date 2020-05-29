package com.example.btncounterapp.music_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.btncounterapp.R
import com.example.btncounterapp.music_app.adapters.ArtistAlbumListAdapter
import com.example.btncounterapp.music_app.adapters.ArtistListAdapter
import com.example.btncounterapp.music_app.adapters.ArtistSongListAdapter
import com.example.btncounterapp.music_app.models.Artist
import com.example.btncounterapp.music_app.models.Song

class ArtistInfoActivity : AppCompatActivity() {


    lateinit var toolbar: Toolbar
    lateinit var artistAlbums: RecyclerView
    lateinit var artistTracks: RecyclerView

    lateinit var authorName: TextView
    lateinit var authorInfo: TextView


    //    adapters
    lateinit var albumsAdapter: ArtistAlbumListAdapter
    lateinit var songsAdapter: ArtistSongListAdapter


    var data = mutableListOf<Artist>(
        Artist(title = "Test", image_url = "url"),
        Artist(title = "Test", image_url = "url"),
        Artist(title = "Test", image_url = "url"),
        Artist(title = "Test", image_url = "url"),
        Artist(title = "Test", image_url = "url"),
        Artist(title = "Test", image_url = "url")


    )

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
        setContentView(R.layout.activity_artist_info)

        toolbar = findViewById(R.id.toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        authorName = findViewById(R.id.author_name)
        authorInfo = findViewById(R.id.author_info)


//
        artistAlbums = findViewById(R.id.artist_albums)
        albumsAdapter = ArtistAlbumListAdapter(data = data)

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
        songsAdapter = ArtistSongListAdapter(data = songs)

        artistTracks.apply {
            layoutManager = LinearLayoutManager(
                applicationContext
            )
            adapter = songsAdapter
        }
        artistTracks.setNestedScrollingEnabled(false)

    }
}
