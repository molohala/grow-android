package com.molohala.infinity.rank.api

import com.molohala.infinity.global.dto.response.BaseResponse
import com.molohala.infinity.rank.response.GithubRankResponse
import retrofit2.http.GET

interface RankApi {

    @GET("/rank/github/week")
    fun getWeekGithubRank(): BaseResponse<List<GithubRankResponse>>

    @GET("/rank/github/total")
    fun getWeekGithubTotal(): BaseResponse<List<GithubRankResponse>>

    @GET("/rank/github/today")
    fun getWeekGithubToday(): BaseResponse<List<GithubRankResponse>>

}