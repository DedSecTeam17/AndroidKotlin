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
import com.example.btncounterapp.R
import com.example.btncounterapp.music_app.ArtistInfoActivity
import com.example.btncounterapp.music_app.TrackPlayerActivity
import com.example.btncounterapp.music_app.models.Song


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

    fun bind(song: Song) {
        songName.text = song.songName
        songAuthor.text = song.songAuthor

        Glide.with(itemView.context)
            .load("https://images.unsplash.com/photo-1589112220796-f266d324ea69?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max")
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL) //3
            .transform(RoundedCorners(10))
            .into(authorImage)

    }

}

class SongListAdapter(var data: List<Song>) :
    RecyclerView.Adapter<SongListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SongListViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SongListViewHolder, position: Int) {
        holder.bind(song = data[position])
        holder.itemView.setOnClickListener { view ->
            var intent: Intent = Intent(view.context, TrackPlayerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            view.context.startActivity(intent)
        }

    }
}