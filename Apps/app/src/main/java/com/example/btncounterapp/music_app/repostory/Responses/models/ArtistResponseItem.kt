package com.example.btncounterapp.music_app.repostory.Responses.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class ArtistResponseItem  (

	@SerializedName("id") val id : Int,
	@SerializedName("artist_name") val artist_name : String,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("updated_at") val updated_at : String,
	@SerializedName("artist_image_url") val artist_image_url : List<Artist_image_url>,
	@SerializedName("albums") val albums : List<Albums>,
	@SerializedName("tracks") val tracks : List<Tracks>
)