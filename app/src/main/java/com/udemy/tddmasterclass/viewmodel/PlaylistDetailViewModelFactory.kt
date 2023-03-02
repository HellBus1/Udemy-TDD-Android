package com.udemy.tddmasterclass.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udemy.tddmasterclass.data.PlaylistDetailService
import com.udemy.tddmasterclass.data.PlaylistRepository
import javax.inject.Inject

class PlaylistDetailViewModelFactory @Inject constructor (
    private val service: PlaylistDetailService
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaylistDetailsViewModel::class.java)) {
            return PlaylistDetailsViewModel(service) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}