package com.example.btncounterapp.music_app.adapters

//import com.example.btncounterapp.music_app.GlideApp
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.btncounterapp.R
import com.example.btncounterapp.music_app.TrackPlayerActivity
import com.example.btncounterapp.music_app.repostory.Responses.models.Tracks
import android.media.MediaMetadataRetriever
import android.net.Uri
import com.example.btncounterapp.music_app.repostory.ApiCalls.ServiceBuilder
import android.media.MediaPlayer
import com.bumptech.glide.util.Util

import java.text.SimpleDateFormat
import java.util.*


class ArtistSongListViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.song_row, parent, false
        )
    ) {
    lateinit private var songName: TextView
    lateinit private var songDuration: TextView


    init {
        songName = itemView.findViewById<TextView>(R.id.song_name)
        songDuration = itemView.findViewById<TextView>(R.id.duration)


    }

    fun bind(song: Tracks) {
//        val uri = Uri.parse("${ServiceBuilder.baseUrl}${song.track_sound_url.get(0).url}")
//        val mp = MediaPlayer.create(itemView.context, uri)

        songName.setText(song.track_name)


//        val mediaMetadataRetriever = MediaMetadataRetriever()
//        mediaMetadataRetriever.setDataSource("${ServiceBuilder.baseUrl}${song.track_sound_url.get(0).url}")
//        var duration = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        songDuration.setText("2:33")


    }

    fun getDate(milliSeconds: Long, dateFormat: String): String {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(milliSeconds)
        return formatter.format(calendar.getTime())
    }

}

class ArtistSongListAdapter(var data: List<Tracks>) :
    RecyclerView.Adapter<ArtistSongListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistSongListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ArtistSongListViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ArtistSongListViewHolder, position: Int) {
        holder.bind(song = data[position])
        holder.itemView.setOnClickListener { view ->
            var intent: Intent = Intent(view.context, TrackPlayerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("id", data[position].id)
            view.context.startActivity(intent)
        }
    }
}