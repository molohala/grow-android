package com.molohala.infinity.data.community.response

import com.molohala.infinity.data.comment.response.CommentResponse
import java.time.LocalDateTime

data class CommunityResponse(
    val community: com.molohala.infinity.data.community.response.CommunityContentResponse,
    val recentComment: com.molohala.infinity.data.comment.response.CommentResponse
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
