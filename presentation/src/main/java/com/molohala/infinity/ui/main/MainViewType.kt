package com.molohala.infinity.ui.main

sealed class MainViewType(
    val name: String
) {
    data object Main: MainViewType(name = "main")
    data object ProfileDetail: MainViewType(name = "profile_detail")
}