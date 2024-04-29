package com.molohala.infinity.data.info.response

data class GithubResponse(
    val name: String,
    val avatarUrl: String,
    val bio: String,
    val totalCommits: Int,
    val weekCommits: List<CommitResponse>,
    val todayCommits: CommitResponse
)