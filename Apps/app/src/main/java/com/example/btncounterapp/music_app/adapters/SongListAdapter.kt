package com.example.btncounterapp.music_app.adapters

import android.content.Intent
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


//import com.example.btncounterapp.music_app.GlideApp
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.btncounterapp.R
import com.example.btncounterapp.music_app.ArtistInfoActivity
import com.example.btncounterapp.music_app.TrackPlayerActivity
import com.example.btncounterapp.music_app.models.Song
import com.example.btncounterapp.music_app.repostory.ApiCalls.ServiceBuilder
import com.example.btncounterapp.music_app.repostory.Responses.models.TrackResponseItem


class SongListViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.track_row, parent, false
        )
    ) {
    private var songName: TextView
    private var songAuthor: TextView
    private var authorImage: ImageView


    init {
        songName = itemView.findViewById<TextView>(R.id.song_title)
        songAuthor = itemView.findViewById<TextView>(R.id.song_author)
        authorImage = itemView.findViewById<ImageView>(R.id.author_image)


    }

    fun bind(track: TrackResponseItem) {
        songName.text = track.track_name
        songAuthor.text = track.artist.artist_name

        Glide.with(itemView.context)
            .load("${ServiceBuilder.baseUrl}${track.artist.artist_image_url.get(0).formats.small.url}")
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL) //3
            .transform(CenterCrop(), RoundedCorners(5))
            .into(authorImage)

    }

}

class SongListAdapter(var data: ArrayList<TrackResponseItem>) :
    RecyclerView.Adapter<SongListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SongListViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SongListViewHolder, position: Int) {
        holder.bind(track = data[position])
        holder.itemView.setOnClickListener { view ->
            var intent: Intent = Intent(view.context, TrackPlayerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("id", data[position].id)

            view.context.startActivity(intent)
        }

    }
}