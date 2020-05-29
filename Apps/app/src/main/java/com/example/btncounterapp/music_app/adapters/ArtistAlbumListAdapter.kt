package com.example.btncounterapp.music_app.adapters

//import com.example.btncounterapp.music_app.GlideApp
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.btncounterapp.R
import com.example.btncounterapp.music_app.AlbumInfoActivity
import com.example.btncounterapp.music_app.ArtistInfoActivity
import com.example.btncounterapp.music_app.repostory.ApiCalls.ServiceBuilder
import com.example.btncounterapp.music_app.repostory.Responses.models.Albums
import com.google.gson.Gson


class ArtistAlbumViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(
    inflater.inflate(
        R.layout.artist_album_row, parent, false
    )
) {
    lateinit private var title: TextView
    lateinit var previewImage: ImageView


    init {
        title = itemView.findViewById<TextView>(R.id.artist_name)
        previewImage = itemView.findViewById<ImageView>(R.id.artist_image_preview)


    }

    fun bind(album: Albums) {
        title.setText(album.album_name)




        Glide.with(itemView.context)
            .load("${ServiceBuilder.baseUrl}${album.album_image_url.get(0).formats?.thumbnail?.url}")
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL) //3

            .transform(RoundedCorners(50))
            .into(previewImage)

    }

}

class ArtistAlbumListAdapter(var data: List<Albums>) :
    RecyclerView.Adapter<ArtistAlbumViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistAlbumViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ArtistAlbumViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ArtistAlbumViewHolder, position: Int) {
        holder.bind(album = data[position])

        holder.itemView.setOnClickListener { view ->
            var intent: Intent = Intent(view.context, AlbumInfoActivity::class.java)
            intent.putExtra("id" ,data[position].id )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            view.context.startActivity(intent)
        }
    }
}