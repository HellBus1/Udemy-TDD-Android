package com.udemy.playlist

import com.udemy.tddmasterclass.data.PlaylistRepository
import com.udemy.tddmasterclass.model.PlaylistItem
import com.udemy.tddmasterclass.viewmodel.PlaylistViewModel
import com.udemy.utils.BaseUnitTest
import com.udemy.utils.captureValues
import com.udemy.utils.getValueForTest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class PlaylistViewModelShould: BaseUnitTest() {
    private val repository: PlaylistRepository = mock()
    private val playlists = mock<List<PlaylistItem>>()

    private val expected = Result.success(playlists)
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistsFromRepository() = runTest {
        val viewModel = mockSuccessfulCase()

        viewModel.playlists.getValueForTest()

        verify(repository, times(1)).getPlaylists()
    }

    @Test
    fun emitsPlaylistsFromRepository() = runTest {
        val viewModel = mockSuccessfulCase()

        assertEquals(expected, viewModel.playlists.getValueForTest())
    }

    @Test
    fun emitsErrorWhenReceiveError() = runTest {
        val viewModel = mockErrorCase()

        assertEquals(exception, viewModel.playlists.getValueForTest()?.exceptionOrNull())
    }

    @Test
    fun showSpinnerWhileLoading() = runTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()
            
            assertEquals(true, values.first())
        }
    }

    @Test
    fun closeLoaderAfterPlaylistLoaded() = runTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    @Test
    fun closeLoaderAfterError() = runTest {
        val viewModel = mockErrorCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    private fun mockSuccessfulCase(): PlaylistViewModel {
        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }

        return PlaylistViewModel(repository)
    }

    private fun mockErrorCase(): PlaylistViewModel {
        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(Result.failure(exception))
                }
            )
        }

        return PlaylistViewModel(repository)
    }
}