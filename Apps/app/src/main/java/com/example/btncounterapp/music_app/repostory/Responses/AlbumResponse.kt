package com.example.btncounterapp.music_app.repostory.Responses

import com.google.android.exoplayer2.extractor.mp4.Track
import java.net.URL


typealias AlbumResponse = ArrayList<AlbumResponseElement>

data class AlbumResponseElement (
    val id: Long,
    val albumName: String,
    val artist: Artist,
    val createdAt: String,
    val updatedAt: String,
    val albumImageURL: List<URL>,
    val tracks: List<Track>
)



data class AlbumImageURLFormats (
    val thumbnail: Small,
    val small: Small
)

data class Small (
    val hash: String,
    val ext: String,
    val mime: String,
    val width: Long,
    val height: Long,
    val size: Double,
    val path: Any? = null,
    val url: String
)

data class Artist (
    val id: Long,
    val artistName: String,
    val createdAt: String,
    val updatedAt: String,
    val artistImageURL: List<ArtistImageURL>
)

data class ArtistImageURL (
    val id: Long,
    val name: String,
    val alternativeText: String,
    val caption: String,
    val width: Long,
    val height: Long,
    val formats: ArtistImageURLFormats,
    val hash: String,
    val ext: String,
    val mime: String,
    val size: Double,
    val url: String,
    val previewURL: Any? = null,
    val provider: String,
    val providerMetadata: Any? = null,
    val createdAt: String,
    val updatedAt: String
)

data class ArtistImageURLFormats (
    val thumbnail: Small,
    val medium: Small,
    val small: Small
)


