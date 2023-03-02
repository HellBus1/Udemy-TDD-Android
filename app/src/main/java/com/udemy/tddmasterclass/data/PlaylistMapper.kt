package com.udemy.tddmasterclass.data

import com.udemy.tddmasterclass.model.PlaylistItem
import com.udemy.tddmasterclass.model.PlaylistRaw
import javax.inject.Inject
import com.udemy.tddmasterclass.R

class PlaylistMapper @Inject constructor() : Function1<List<PlaylistRaw>, List<PlaylistItem>> {
    override fun invoke(p1: List<PlaylistRaw>): List<PlaylistItem> {
        return p1.map {
            val image = when (it.category) {
                "rock" -> R.drawable.ic_launcher_foreground
                else -> R.drawable.ic_launcher_background
            }

            PlaylistItem(it.id, it.name, it.category, image)
        }
    }
}