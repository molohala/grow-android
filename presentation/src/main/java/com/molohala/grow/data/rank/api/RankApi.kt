package com.molohala.grow.data.rank.api

import com.molohala.grow.data.global.dto.response.BaseResponse
import com.molohala.grow.data.rank.response.UpdateRankResponse
import retrofit2.http.GET

interface RankApi {

    @GET("/rank/github/week")
    suspend fun getWeekGithubRank(): BaseResponse<UpdateRankResponse>

    @GET("/rank/github/total")
    suspend fun getTotalGithubRank(): BaseResponse<UpdateRankResponse>

    @GET("/rank/github/today")
    suspend fun getTodayGithubRank(): BaseResponse<UpdateRankResponse>

    @GET("/rank/solvedac/week")
    suspend fun getWeekSolvedacRank(): BaseResponse<UpdateRankResponse>

    @GET("/rank/solvedac/total")
    suspend fun getTotalSolvedacRank(): BaseResponse<UpdateRankResponse>

    @GET("/rank/solvedac/today")
    suspend fun getTodaySolvedacRank(): BaseResponse<UpdateRankResponse>

}