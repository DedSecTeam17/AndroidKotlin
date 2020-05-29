package com.example.btncounterapp.music_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.btncounterapp.R

class TrackPlayerActivity : AppCompatActivity() {



    lateinit var albumImage : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_player)
        albumImage = findViewById(R.id.album_image)
        Glide.with(this)
            .load("https://images.unsplash.com/photo-1590615428797-ad80b4e5f554?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max")
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL) //3

            .transform(RoundedCorners(20))
            .into(albumImage)
    }
}
