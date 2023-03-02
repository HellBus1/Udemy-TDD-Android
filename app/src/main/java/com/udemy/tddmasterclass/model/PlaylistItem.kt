package com.udemy.tddmasterclass.model

import com.udemy.tddmasterclass.R

data class PlaylistItem(
    val id: String,
    val name: String,
    val category: String,
    val image: Int = R.drawable.ic_launcher_background
)