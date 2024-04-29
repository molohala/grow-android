package com.molohala.infinity.data.rank.api

import com.molohala.infinity.data.global.dto.response.BaseResponse
import com.molohala.infinity.data.rank.response.GithubRankResponse
import retrofit2.http.GET

interface RankApi {

    @GET("/rank/github/week")
    suspend fun getWeekGithubRank(): BaseResponse<List<GithubRankResponse>>

    @GET("/rank/github/total")
    suspend fun getTotalGithubRank(): BaseResponse<List<GithubRankResponse>>

    @GET("/rank/github/today")
    suspend fun getTodayGithubRank(): BaseResponse<List<GithubRankResponse>>

}