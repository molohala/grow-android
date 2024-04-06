package com.molohala.infinity.ui.root

sealed class RootViewType(
    val name: String
) {
    data object SignIn: RootViewType(name = "sign_in")
    data object Main: RootViewType(name = "main")
}