package com.example.btncounterapp.music_app

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrackPlayerActivity : AppCompatActivity() {


    lateinit var albumImage: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_player)
        albumImage = findViewById(R.id.album_image)


        getData(intent.extras?.getInt("id"))
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
                    Glide.with(applicationContext)
                        .load(
                            "${ServiceBuilder.baseUrl}${response.body()?.album?.album_image_url?.get(
                                0
                            )?.url}"
                        )
                        .thumbnail(0.1f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL) //3
                        .transform(CenterCrop(), RoundedCorners(15))
                        .into(albumImage)

                }
            })
    }

}
