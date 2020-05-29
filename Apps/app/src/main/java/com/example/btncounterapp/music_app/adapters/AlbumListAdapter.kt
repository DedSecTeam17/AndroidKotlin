package com.example.btncounterapp.music_app.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.btncounterapp.R
import com.example.btncounterapp.music_app.AlbumInfoActivity
import com.example.btncounterapp.music_app.repostory.ApiCalls.ServiceBuilder
import com.example.btncounterapp.music_app.repostory.Responses.models.AlbumResponseItem
import com.google.gson.Gson

class AlbumViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(
    inflater.inflate(
        R.layout.album_row, parent, false
    )
) {
    private var aritstName: TextView
    private var albumName: TextView

    var previewImage: ImageView


    init {
        aritstName = itemView.findViewById<TextView>(R.id.artist_name)
        albumName = itemView.findViewById<TextView>(R.id.album_name)

        previewImage =
            itemView.findViewById<ImageView>(R.id.artist_image_preview)


    }

    fun bind(album: AlbumResponseItem) {
        aritstName.text = album.artist.artist_name
        albumName.text = album.album_name


//        System.out.println("${ServiceBuilder.baseUrl}${artist.artistImageURL.get(0).formats?.thumbnail?.url}")
        val options = RequestOptions()
        options.centerCrop()
        options.transform(RoundedCorners(50))
        Glide.with(itemView.context)
            .load("${ServiceBuilder.baseUrl}${album.album_image_url.get(0).formats?.thumbnail?.url}")
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL) //3
            .transform(CenterCrop(), RoundedCorners(15))

            .into(previewImage)

    }

}

class AlbumListAdapter(var data: ArrayList<AlbumResponseItem>) :
    RecyclerView.Adapter<AlbumViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AlbumViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(album = data[position])

        holder.itemView.setOnClickListener { view ->
            var intent: Intent = Intent(view.context, AlbumInfoActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("id" , data[position].id)

            view.context.startActivity(intent)

        }
    }
}

