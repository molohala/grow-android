package com.molohala.infinity.ui.main.main

sealed class NavGroup(
    val name: String
) {
    data object SignIn: NavGroup(name = "sign_in")
    data object Main: NavGroup(name = "main")
    data object ProfileDetail: NavGroup(name = "profile_detail")
    data object Setting: NavGroup(name = "setting")
    data object ProfileEdit: NavGroup(name = "profile_edit")
    data object GithubSetting: NavGroup(name = "github_setting")
    data object BaekjoonSetting: NavGroup(name = "baekjoon_setting")
    data object CreateCommunity: NavGroup(name = "create_community")
}