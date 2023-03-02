package com.udemy.playlistdetail

import com.udemy.tddmasterclass.data.PlaylistAPI
import com.udemy.tddmasterclass.data.PlaylistDetailService
import com.udemy.tddmasterclass.model.PlaylistDetails
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

class PlaylistDetailsServiceShould: BaseUnitTest() {
    private val playlistAPI: PlaylistAPI = mock()
    private lateinit var service: PlaylistDetailService
    private val playlistDetails: PlaylistDetails = mock()
    private val id = "1"

    @Test
    fun fetchPlaylistDetailsFromAPI() = runTest {
        mockSuccessfulCase()

        service.fetchPlaylistDetails(id).first()

        verify(playlistAPI, times(1)).fetchPlaylistDetails(id)
    }

    @Test
    fun convertValuesToFlowResultAndEmitThem() = runTest {
        mockSuccessfulCase()

        assertEquals(Result.success(playlistDetails), service.fetchPlaylistDetails(id).first())
    }

    @Test
    fun emitsErrorResultWhenNetworkFails() = runTest {
        mockFailureCase()

        assertEquals("Something went wrong", service.fetchPlaylistDetails(id).first().exceptionOrNull()?.message)
    }

    private fun mockSuccessfulCase() {
        runBlocking {
            whenever(playlistAPI.fetchPlaylistDetails(id)).thenReturn(
                playlistDetails
            )
        }

        service = PlaylistDetailService(playlistAPI)
    }

    private fun mockFailureCase() {
        runBlocking {
            whenever(playlistAPI.fetchPlaylistDetails(id)).thenThrow(
                RuntimeException("Something went wrong")
            )
        }

        service = PlaylistDetailService(playlistAPI)
    }

}