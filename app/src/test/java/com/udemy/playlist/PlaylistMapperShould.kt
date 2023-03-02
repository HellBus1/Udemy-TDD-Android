package com.udemy.playlist

import com.udemy.tddmasterclass.data.PlaylistMapper
import com.udemy.tddmasterclass.model.PlaylistRaw
import com.udemy.utils.BaseUnitTest
import org.junit.Assert.assertEquals
import org.junit.Test
import com.udemy.tddmasterclass.R

class PlaylistMapperShould : BaseUnitTest() {
    private val playlistRaw = PlaylistRaw("1", "da name", "da category")
    private val playlistRock = PlaylistRaw("1", "da name", "rock")

    private val mapper = PlaylistMapper()

    private val playlistItem = mapper(listOf(playlistRaw)).first()
    private val playlistsRockItem = mapper(listOf(playlistRock)).first()

    @Test
    fun keepSameId() {
        assertEquals(playlistRaw.id, playlistItem.id)
    }

    @Test
    fun keepSameName() {
        assertEquals(playlistRaw.name, playlistItem.name)
    }

    @Test
    fun keepSameCategory() {
        assertEquals(playlistRaw.category, playlistItem.category)
    }

    @Test
    fun mapDefaultImageWhenNotRock() {
        assertEquals(R.drawable.ic_launcher_background, playlistItem.image)
    }

    @Test
    fun mapRockImageWhenRockCategory() {
        assertEquals(R.drawable.ic_launcher_foreground, playlistsRockItem.image)
    }
}