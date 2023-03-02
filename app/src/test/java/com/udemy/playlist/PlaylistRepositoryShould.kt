package com.udemy.playlist

import com.udemy.tddmasterclass.data.PlaylistMapper
import com.udemy.tddmasterclass.data.PlaylistRepository
import com.udemy.tddmasterclass.data.PlaylistService
import com.udemy.tddmasterclass.model.PlaylistItem
import com.udemy.tddmasterclass.model.PlaylistRaw
import com.udemy.utils.BaseUnitTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PlaylistRepositoryShould: BaseUnitTest() {
    private val service: PlaylistService = mock()
    private val mapper: PlaylistMapper = mock()
    private val playlists = mock<List<PlaylistItem>>()
    private val playlistsRaw = mock<List<PlaylistRaw>>()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getsPlaylistFromService() = runTest {
        val repository = PlaylistRepository(service, mapper)

        repository.getPlaylists()

        verify(service, times(1)).fetchPlaylists()
    }

    @Test
    fun emitsMappedPlaylistFromService() = runTest {
        val repository = mockSuccesfulCase()

        assertEquals(playlists, repository.getPlaylists().first().getOrNull())
    }

    @Test
    fun propagateError() = runTest {
        val repository = mockFailureCase()

        assertEquals(exception, repository.getPlaylists().first().exceptionOrNull())
    }

    @Test
    fun delegateBusinessLogicToMapper() = runTest {
        val repository = mockSuccesfulCase()

        repository.getPlaylists().first()

        verify(mapper, times(1)).invoke(playlistsRaw)
    }

    private fun mockSuccesfulCase(): PlaylistRepository {
        runBlocking {
            whenever(service.fetchPlaylists()).thenReturn(
                flow {
                    emit(Result.success(playlistsRaw))
                }
            )
        }

        whenever(mapper.invoke(playlistsRaw)).thenReturn(playlists)

        return PlaylistRepository(service, mapper)
    }

    private fun mockFailureCase(): PlaylistRepository {
        runBlocking {
            whenever(service.fetchPlaylists()).thenReturn(
                flow {
                    emit(Result.failure(exception))
                }
            )
        }

        return PlaylistRepository(service, mapper)
    }
}