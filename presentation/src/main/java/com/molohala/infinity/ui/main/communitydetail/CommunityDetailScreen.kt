package com.molohala.infinity.ui.main.communitydetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.molohala.infinity.common.flow.FetchFlow
import com.molohala.infinity.ui.root.AppViewModel

@Composable
fun CommunityDetailScreen(
    appViewModel: AppViewModel,
    viewModel: CommunityDetailViewModel = viewModel()
) {

    val uiAppState by appViewModel.uiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Box {
        LazyColumn {
            item {
                uiState.community.let {
                    when (it) {
                        is FetchFlow.Failure -> Text(text = "불러오기 실패")
                        is FetchFlow.Fetching -> {

                        }
                        is FetchFlow.Success -> TODO()
                    }
                }
            }
        }
    }
}