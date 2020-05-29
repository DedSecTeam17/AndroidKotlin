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
import com.example.btncounterapp.music_app.models.Artist
import com.example.btncounterapp.music_app.utls.AppActions


class ArtistViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(
    inflater.inflate(
        R.layout.artist_row, parent, false
    )
) {
    lateinit private var title: TextView
    lateinit var previewImage: ImageView


    init {
        title = itemView.findViewById<TextView>(R.id.artist_name)
        previewImage = itemView.findViewById<ImageView>(R.id.artist_image_preview)


    }

    fun bind(artist: Artist) {
        title.setText(artist.title)





        Glide.with(itemView.context)
            .load("https://images.unsplash.com/photo-1590615428797-ad80b4e5f554?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max")
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL) //3

            .transform(RoundedCorners(50))
            .into(previewImage)

    }

}

class ArtistListAdapter(var data: List<Artist>, var action: AppActions) :
    RecyclerView.Adapter<ArtistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ArtistViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(artist = data[position])

        holder.itemView.setOnClickListener { view ->
            if (action == AppActions.ALBUM) {
                var intent: Intent = Intent(view.context, AlbumInfoActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                view.context.startActivity(intent)
            } else {
                var intent: Intent = Intent(view.context, ArtistInfoActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                view.context.startActivity(intent)
            }

        }
    }
}