package com.udemy.tddmasterclass.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udemy.tddmasterclass.data.PlaylistRepository
import javax.inject.Inject

class PlaylistViewModelFactory @Inject constructor (
    private val repository: PlaylistRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaylistViewModel::class.java)) {
            return PlaylistViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}