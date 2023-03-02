package com.udemy.tddmasterclass.data

import com.udemy.tddmasterclass.model.PlaylistDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistDetailService @Inject constructor(
    private val playlistAPI: PlaylistAPI
) {
    suspend fun fetchPlaylistDetails(id: String): Flow<Result<PlaylistDetails>> =
        flow {
            emit(Result.success(playlistAPI.fetchPlaylistDetails(id)))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
}