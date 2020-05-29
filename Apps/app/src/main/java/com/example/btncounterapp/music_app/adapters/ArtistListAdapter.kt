package com.example.btncounterapp.music_app.adapters

//import com.example.btncounterapp.music_app.GlideApp
import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.btncounterapp.R
import com.example.btncounterapp.music_app.AlbumInfoActivity
import com.example.btncounterapp.music_app.ArtistInfoActivity
import com.example.btncounterapp.music_app.models.Artist
import com.example.btncounterapp.music_app.repostory.ApiCalls.ServiceBuilder
import com.example.btncounterapp.music_app.repostory.Responses.models.ArtistResponseItem
//import com.example.btncounterapp.music_app.repostory.Responses.ArtistModelElement
//import com.example.btncounterapp.music_app.repostory.Responses.ArtistResponse
import com.example.btncounterapp.music_app.utls.AppActions
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.bumptech.glide.request.RequestOptions
import com.example.btncounterapp.music_app.repostory.Responses.models.AlbumResponseItem
import com.google.gson.Gson


class ArtistViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(
    inflater.inflate(
        R.layout.artist_row, parent, false
    )
) {
    private var title: TextView
    var previewImage: ImageView


    init {
        title = itemView.findViewById<TextView>(R.id.artist_name)
        previewImage =
            itemView.findViewById<ImageView>(com.example.btncounterapp.R.id.artist_image_preview)


    }

    fun bind(artist: ArtistResponseItem) {
        title.setText(artist.artist_name)


//        System.out.println("${ServiceBuilder.baseUrl}${artist.artistImageURL.get(0).formats?.thumbnail?.url}")
        val options = RequestOptions()
        options.centerCrop()
        options.transform(RoundedCorners(50))
        Glide.with(itemView.context)
            .load("${ServiceBuilder.baseUrl}${artist.artist_image_url.get(0).formats?.thumbnail?.url}")
            .thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL) //3
            .transform(CenterCrop(), RoundedCorners(15))

            .into(previewImage)

    }

}


//


class ArtistListAdapter(var data: ArrayList<ArtistResponseItem>, var action: AppActions) :
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
                var gson: Gson = Gson()
                intent.putExtra("data", gson.toJson(data[position]))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                view.context.startActivity(intent)
            }

        }
    }
}


