package com.molohala.grow.ui.main.main

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
    data object CreateForum: NavGroup(name = "create_forum")
    data object ForumDetail: NavGroup(name = "forum_detail") {
        const val FORUM_ID = "forum_id"
    }
    data object EditForum : NavGroup(name = "edit_forum") {
        const val FORUM_ID = "forum_id"
    }
}