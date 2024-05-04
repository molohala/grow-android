package com.molohala.grow.data.community.response

import com.molohala.grow.data.comment.response.CommentResponse
import java.time.LocalDateTime

data class CommunityResponse(
    val community: CommunityContentResponse,
    val recentComment: CommentResponse?
) {
    companion object {
        fun dummy(
            liked: Boolean = true,
            recentComment: CommentResponse? = CommentResponse.dummy()
        ) = CommunityResponse(
            community = CommunityContentResponse.dummy(liked),
            recentComment = recentComment
        )
    }
}

data class CommunityContentResponse(
    val communityId: Int,
    val content: String,
    val createdAt: LocalDateTime,
    val like: Int,
    val writerName: String,
    val writerId: Int,
    val liked: Boolean
) {
    companion object {
        fun dummy(
            liked: Boolean
        ) = CommunityContentResponse(
            communityId = 0,
            content = "우와 지존입니다 ㅋ 응",
            createdAt = LocalDateTime.now(),
            like = 311,
            writerName = "이강현",
            writerId = 0,
            liked = liked
        )
    }
}
