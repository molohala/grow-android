package com.molohala.infinity.data.rank.api

import com.molohala.infinity.data.global.dto.response.BaseResponse
import com.molohala.infinity.data.rank.response.RankResponse
import retrofit2.http.GET

interface RankApi {

    @GET("/rank/github/week")
    suspend fun getWeekGithubRank(): BaseResponse<List<RankResponse>>

    @GET("/rank/github/total")
    suspend fun getTotalGithubRank(): BaseResponse<List<RankResponse>>

    @GET("/rank/github/today")
    suspend fun getTodayGithubRank(): BaseResponse<List<RankResponse>>

    @GET("/rank/solvedac/week")
    suspend fun getWeekSolvedacRank(): BaseResponse<List<RankResponse>>

    @GET("/rank/solvedac/total")
    suspend fun getTotalSolvedacRank(): BaseResponse<List<RankResponse>>

    @GET("/rank/solvedac/today")
    suspend fun getTodaySolvedacRank(): BaseResponse<List<RankResponse>>

}