package com.udemy.playlist

import com.udemy.tddmasterclass.data.PlaylistAPI
import com.udemy.tddmasterclass.data.PlaylistService
import com.udemy.tddmasterclass.model.PlaylistItem
import com.udemy.tddmasterclass.model.PlaylistRaw
import com.udemy.utils.BaseUnitTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PlaylistServiceShould: BaseUnitTest() {
    private val playListAPI: PlaylistAPI = mock()
    private val playlistsRaw = mock<List<PlaylistRaw>>()

    @Test
    fun fetchPlaylistsFromPlaylistAPI() = runTest {
        val service = PlaylistService(playListAPI)

        service.fetchPlaylists().first()

        verify(playListAPI, times(1)).fetchAllPlaylists()
    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runTest {
        val service = mockSuccessfulCase()

        assertEquals(Result.success(playlistsRaw), service.fetchPlaylists().first())
    }

    @Test
    fun emitsErrorResultWhenNetworkFails() = runTest {
        val service = mockFailureCase()

        assertEquals("Something went wrong",
            service.fetchPlaylists().first().exceptionOrNull()?.message)
    }

    private fun mockFailureCase(): PlaylistService {
        runBlocking {
            whenever(playListAPI.fetchAllPlaylists()).thenThrow(
                RuntimeException("Something went wrong")
            )
        }

        return PlaylistService(playListAPI)
    }

    private fun mockSuccessfulCase(): PlaylistService {
        runBlocking {
            whenever(playListAPI.fetchAllPlaylists()).thenReturn(playlistsRaw)
        }

        return PlaylistService(playListAPI)
    }
}