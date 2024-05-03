package com.molohala.infinity.ui.main.createcommunity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molohala.infinity.common.flow.IdleFlow
import com.molohala.infinity.data.community.request.CreateCommunityRequest
import com.molohala.infinity.data.global.RetrofitClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CreateCommunityState(
    val content: String = "",
    val createCommunityFetchFlow: IdleFlow = IdleFlow.Idle
)

sealed interface CreateCommunitySideEffect {
    data object Success: CreateCommunitySideEffect
    data object Failure: CreateCommunitySideEffect
}

class CreateCommunityViewModel: ViewModel() {

    private val _uiEffect = MutableSharedFlow<CreateCommunitySideEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private val _uiState = MutableStateFlow(CreateCommunityState())
    val uiState = _uiState.asStateFlow()

    fun createCommunity() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(createCommunityFetchFlow = IdleFlow.Fetching) }
                val request = CreateCommunityRequest(content = _uiState.value.content)
                RetrofitClient.communityApi.createCommunity(request)
                _uiState.update { it.copy(createCommunityFetchFlow = IdleFlow.Success) }
                _uiEffect.emit(CreateCommunitySideEffect.Success)
            } catch (e: Exception) {
                _uiState.update { it.copy(createCommunityFetchFlow = IdleFlow.Failure) }
                _uiEffect.emit(CreateCommunitySideEffect.Failure)
            }
        }
    }

    fun updateContent(content: String) {
        _uiState.update { it.copy(content = content) }
    }
}