package com.molohala.grow.ui.main.createforum

import androidx.lifecycle.ViewModel
import com.molohala.grow.data.forum.request.PatchForumRequest
import com.molohala.grow.data.global.RetrofitClient
import com.molohala.grow.ui.util.launch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class EditForumState(
    val content: String = ""
)

sealed interface EditForumSideEffect {
    data object Success: EditForumSideEffect
    data object Failure: EditForumSideEffect
}

class EditForumViewModel: ViewModel() {

    private val _uiEffect = MutableSharedFlow<EditForumSideEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private val _uiState = MutableStateFlow(EditForumState())
    val uiState = _uiState.asStateFlow()

    fun fetchForum(forumId: Int) {
        launch {
            try {
                val forum = RetrofitClient.forumApi.getForum(id = forumId).data?: return@launch
                _uiState.update { it.copy(content = forum.content) }
            } catch (e: Exception) {
            }
        }
    }

    fun editForum(forumId: Int) {
        launch {
            try {
                val request = PatchForumRequest(content = _uiState.value.content, id = forumId)
                RetrofitClient.forumApi.patchForum(request)
                _uiEffect.emit(EditForumSideEffect.Success)
            } catch (e: Exception) {
                _uiEffect.emit(EditForumSideEffect.Failure)
            }
        }
    }

    fun updateContent(content: String) {
        _uiState.update { it.copy(content = content) }
    }
}