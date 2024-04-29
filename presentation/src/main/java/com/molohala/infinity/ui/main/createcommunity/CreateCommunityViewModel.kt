package com.molohala.infinity.ui.main.createcommunity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molohala.infinity.common.flow.FetchFlow
import com.molohala.infinity.data.community.request.CreateCommunityRequest
import com.molohala.infinity.data.global.RetrofitClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CreateCommunityState(
    val content: String = "",
    val createCommunityFetchFlow: FetchFlow = FetchFlow.Fetching
)

sealed interface CreateCommunitySideEffect {
    data object Success: CreateCommunitySideEffect
    data object Failure: CreateCommunitySideEffect
}

class CreateCommunityViewModel: ViewModel() {

    val uiEffect = MutableSharedFlow<CreateCommunitySideEffect>()
    val uiState = MutableStateFlow(CreateCommunityState())

    fun createCommunity() {
        viewModelScope.launch {
            try {
                uiState.update { it.copy(createCommunityFetchFlow = FetchFlow.Fetching) }
                val request = CreateCommunityRequest(content = uiState.value.content)
                RetrofitClient.communityApi.createCommunity(request)
                uiState.update { it.copy(createCommunityFetchFlow = FetchFlow.Success) }
                uiEffect.emit(CreateCommunitySideEffect.Success)
            } catch (e: Exception) {
                uiState.update { it.copy(createCommunityFetchFlow = FetchFlow.Failure) }
                uiEffect.emit(CreateCommunitySideEffect.Failure)
            }
        }
    }

    fun updateContent(content: String) {
        uiState.update { it.copy(content = content) }
    }
}