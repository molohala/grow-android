package com.molohala.grow.ui.main.home

import androidx.lifecycle.ViewModel
import com.molohala.grow.common.flow.FetchFlow
import com.molohala.grow.data.forum.response.ForumResponse
import com.molohala.grow.data.global.RetrofitClient
import com.molohala.grow.data.rank.response.RankResponse
import com.molohala.grow.data.rank.response.UpdateRankResponse
import com.molohala.grow.data.report.request.ReportRequest
import com.molohala.grow.ui.util.launch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class HomeState(
    val weekForums: FetchFlow<List<ForumResponse>> = FetchFlow.Fetching(),
    val todayGithubRanks: FetchFlow<UpdateRankResponse> = FetchFlow.Fetching(),
    val todayBaekjoonRanks: FetchFlow<UpdateRankResponse> = FetchFlow.Fetching(),
    val isRefresh: Boolean = false,
    val reportCommentReason: String = "",
    val selectedReportForum: ForumResponse? = null
)

sealed interface HomeSideEffect {
    data object RemoveForumSuccess : HomeSideEffect
    data object RemoveForumFailure : HomeSideEffect
    data object ReportForumSuccess : HomeSideEffect
}

class HomeViewModel : ViewModel() {

    private val _uiEffect = MutableSharedFlow<HomeSideEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    fun fetchTodayGithubRank() {
        launch {
            try {
                _uiState.update { it.copy(todayGithubRanks = FetchFlow.Fetching()) }
                val ranks = RetrofitClient.rankApi.getTodayGithubRank().data ?: return@launch
                if (ranks.ranks.size > 3) {
                    ranks.ranks = ranks.ranks.slice(0..<3)
                }
                _uiState.update {
                    it.copy(
                        todayGithubRanks = FetchFlow.Success(ranks)
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(todayGithubRanks = FetchFlow.Failure()) }
            }
        }
    }

    fun fetchTodayBaekjoonRank() {
        launch {
            try {
                _uiState.update { it.copy(todayBaekjoonRanks = FetchFlow.Fetching()) }
                val ranks = RetrofitClient.rankApi.getTodaySolvedacRank().data ?: return@launch
                if (ranks.ranks.size > 3) {
                    ranks.ranks = ranks.ranks.slice(0..<3)
                }
                _uiState.update {
                    it.copy(
                        todayBaekjoonRanks = FetchFlow.Success(ranks)
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(todayBaekjoonRanks = FetchFlow.Failure()) }
            }
        }
    }

    fun fetchWeekCommunities() {
        launch {
            try {
                _uiState.update { it.copy(weekForums = FetchFlow.Fetching()) }
                val communities = RetrofitClient.forumApi.getBestForums().data ?: return@launch
                _uiState.update {
                    it.copy(
                        weekForums = FetchFlow.Success(communities)
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(weekForums = FetchFlow.Failure()) }
            }
        }
    }

    fun patchLike(forumId: Int) {
        val forums =
            ((_uiState.value.weekForums as? FetchFlow.Success)?.data ?: return).toMutableList()
        launch {
            try {
                RetrofitClient.likeApi.patchLike(forumId)
                forums.forEachIndexed { idx, i ->
                    if (i.forum.forumId == forumId) {
                        val forum = forums[idx]
                        val added = if (forum.forum.liked) -1 else 1
                        forums[idx] = forum.copy(
                            forum = forum.forum.copy(
                                like = forum.forum.like + added,
                                liked = !forum.forum.liked
                            )
                        )
                    }
                }
                _uiState.update { it.copy(weekForums = FetchFlow.Success(forums)) }
            } catch (_: Exception) {
            }
        }
    }

    fun removeForum(forumId: Int) {
        launch {
            try {
                RetrofitClient.forumApi.removeForum(forumId)
                fetchWeekCommunities()
                _uiEffect.emit(HomeSideEffect.RemoveForumSuccess)
            } catch (e: Exception) {
                _uiEffect.emit(HomeSideEffect.RemoveForumFailure)
            }
        }
    }

    fun reportForum() {

        val forum = _uiState.value.selectedReportForum ?: return
        val reason = _uiState.value.reportCommentReason

        launch {
            try {
                val request = ReportRequest(
                    reason = reason
                )
                RetrofitClient.commentApi.reportComment(
                    id = forum.forum.forumId,
                    req = request
                )
                _uiState.update { it.copy(reportCommentReason = "") }
                _uiEffect.emit(HomeSideEffect.ReportForumSuccess)
            } catch (_: Exception) {
            }
        }
    }

    fun updateReportForum(forum: ForumResponse) {
        _uiState.update { it.copy(selectedReportForum = forum) }
    }

    fun updateReportForumReason(reason: String) {
        _uiState.update { it.copy(reportCommentReason = reason) }
    }

    fun refresh() {
        fetchTodayGithubRank()
        fetchTodayBaekjoonRank()
        fetchWeekCommunities()
    }
}