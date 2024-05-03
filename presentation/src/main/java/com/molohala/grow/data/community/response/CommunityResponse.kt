package com.molohala.grow.data.community.response

import com.molohala.grow.data.comment.response.CommentResponse
import java.time.LocalDateTime

data class CommunityResponse(
    val community: CommunityContentResponse,
    val recentComment: CommentResponse?
)

data class CommunityContentResponse(
    val communityId: Int,
    val content: String,
    val createdAt: LocalDateTime,
    val like: Int,
    val writerName: String,
    val writerId: Int,
    val liked: Boolean
)
