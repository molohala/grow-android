package com.molohala.grow.ui.main.createforum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molohala.grow.common.flow.IdleFlow
import com.molohala.grow.data.forum.request.CreateForumRequest
import com.molohala.grow.data.global.RetrofitClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CreateForumState(
    val content: String = "",
    val createForumFlow: IdleFlow = IdleFlow.Idle
)

sealed interface CreateForumSideEffect {
    data object Success: CreateForumSideEffect
    data object Failure: CreateForumSideEffect
}

class CreateForumViewModel: ViewModel() {

    private val _uiEffect = MutableSharedFlow<CreateForumSideEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private val _uiState = MutableStateFlow(CreateForumState())
    val uiState = _uiState.asStateFlow()

    fun createForum() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(createForumFlow = IdleFlow.Fetching) }
                val request = CreateForumRequest(content = _uiState.value.content)
                RetrofitClient.forumApi.createForum(request)
                _uiState.update { it.copy(createForumFlow = IdleFlow.Success) }
                _uiEffect.emit(CreateForumSideEffect.Success)
            } catch (e: Exception) {
                _uiState.update { it.copy(createForumFlow = IdleFlow.Failure) }
                _uiEffect.emit(CreateForumSideEffect.Failure)
            }
        }
    }

    fun updateContent(content: String) {
        _uiState.update { it.copy(content = content) }
    }
}