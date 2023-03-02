package com.udemy.tddmasterclass

sealed class Routes(val route: String) {
    object PlayListPage: Routes(route = "play_list_page")
    object PlayListDetailPage: Routes(route = "play_list_detail_page/{id}")
}