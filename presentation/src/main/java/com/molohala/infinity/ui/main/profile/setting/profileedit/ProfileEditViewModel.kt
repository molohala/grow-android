package com.molohala.infinity.ui.main.profile.setting.profileedit

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update


class ProfileEditViewModel: ViewModel() {
    data class State(
        val jobs: ArrayList<String> = arrayListOf()
    )

    var uiState = MutableStateFlow(State())
}