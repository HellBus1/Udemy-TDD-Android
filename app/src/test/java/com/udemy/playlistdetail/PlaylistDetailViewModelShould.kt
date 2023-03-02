package com.udemy.playlistdetail

import com.udemy.tddmasterclass.data.PlaylistDetailService
import com.udemy.tddmasterclass.model.PlaylistDetails
import com.udemy.tddmasterclass.viewmodel.PlaylistDetailsViewModel
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

class PlaylistDetailViewModelShould: BaseUnitTest() {
    lateinit var viewModel: PlaylistDetailsViewModel
    private val id = "1"
    private val service: PlaylistDetailService = mock()

    private val playlistDeatils: PlaylistDetails = mock()
    private val expected = Result.success(playlistDeatils)
    private val exception = RuntimeException("Something went wrong")
    private val error = Result.failure<PlaylistDetails>(exception)

    @Test
    fun getPlaylistDetailsFromService() = runTest {
        mockSuccessfulCase()
        viewModel.getPlaylistDetails(id)

        viewModel.playlistDetails.getValueForTest()

        verify(service, times(1)).fetchPlaylistDetails(id)
    }

    @Test
    fun emitPlaylistDetailFromService() = runTest {
        mockSuccessfulCase()
        viewModel.getPlaylistDetails(id)

        assertEquals(expected, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun emitErrorWhenServiceFails() = runTest {
        mockErrorCase()

        assertEquals(error, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun showSpinnerWhileLoading() = runTest {
        mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.getPlaylistDetails(id)
            viewModel.playlistDetails.getValueForTest()

            assertEquals(true, values[0])
        }
    }

    @Test
    fun closeLoaderAfterPlaylistDetailLoaded() = runTest {
        mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.getPlaylistDetails(id)
            viewModel.playlistDetails.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    @Test
    fun closeLoaderAfterError() = runTest {
        mockErrorCase()

        viewModel.loader.captureValues {
            viewModel.playlistDetails.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    private fun mockSuccessfulCase() {
        runBlocking {
            whenever(service.fetchPlaylistDetails(id)).thenReturn(
                flow {
                    emit(expected)
                }
            )

            viewModel = PlaylistDetailsViewModel(service)
        }
    }

    private fun mockErrorCase() {
        runBlocking {
            whenever(service.fetchPlaylistDetails(id)).thenReturn(
                flow {
                    emit(error)
                }
            )
        }

        viewModel = PlaylistDetailsViewModel(service)
        viewModel.getPlaylistDetails(id)
    }
}