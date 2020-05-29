package com.example.btncounterapp.music_app.repostory.ApiCalls.Calls

import com.example.btncounterapp.music_app.repostory.Responses.models.ArtistResponseItem
import com.example.btncounterapp.music_app.repostory.Responses.models.AlbumResponseItem
import com.example.btncounterapp.music_app.repostory.Responses.models.TrackResponseItem
//import com.example.btncounterapp.music_app.repostory.Responses.ArtistResponse
//import com.example.btncounterapp.music_app.repostory.Responses.TrackResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AppApiCalls {


    @GET("artists")
    fun getArtists(): Call<ArrayList<ArtistResponseItem>>


    @GET("albums")
    fun getAlbums(): Call<ArrayList<AlbumResponseItem>>


    @GET("tracks")
    fun getTracks(): Call<ArrayList<TrackResponseItem>>


    @GET("albums/{id}")
    fun getAlbumInfo(@Path("id") id: Int?): Call<AlbumResponseItem>


    @GET("tracks/{id}")
    fun getTrackInfo(@Path("id") id: Int?): Call<TrackResponseItem>


}