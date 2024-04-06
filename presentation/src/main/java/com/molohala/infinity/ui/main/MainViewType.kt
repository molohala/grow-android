package com.molohala.infinity.ui.main

sealed class MainViewType(
    val name: String
) {
    data object Main: MainViewType(name = "main")
    data object ProfileDetail: MainViewType(name = "profile_detail")
    data object Setting: MainViewType(name = "setting")
    data object ProfileEdit: MainViewType(name = "profile_edit")
    data object GithubSetting: MainViewType(name = "github_setting")
    data object BaekjoonSetting: MainViewType(name = "baekjoon_setting")
}