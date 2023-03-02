package com.udemy.tddmasterclass.data

import com.udemy.tddmasterclass.model.PlaylistDetails
import com.udemy.tddmasterclass.model.PlaylistItem
import com.udemy.tddmasterclass.model.PlaylistRaw
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaylistAPI {
    @GET("playlist")
    suspend fun fetchAllPlaylists(): List<PlaylistRaw>

    @GET("playlist-detail/{id}")
    suspend fun fetchPlaylistDetails(@Path("id") id: String): PlaylistDetails
}