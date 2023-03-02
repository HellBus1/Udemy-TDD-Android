package com.udemy.tddmasterclass.viewmodel

import androidx.lifecycle.*
import com.udemy.tddmasterclass.data.PlaylistRepository
import com.udemy.tddmasterclass.model.PlaylistItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val repository: PlaylistRepository
): ViewModel() {
    val playlists: LiveData<Result<List<PlaylistItem>>> = liveData {
        loader.postValue(true)
        emitSource(repository.getPlaylists()
            .onEach {
                loader.postValue(false)
            }.asLiveData())
    }

    val loader = MutableLiveData<Boolean>()
}
