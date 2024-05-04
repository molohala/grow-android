package com.molohala.grow.data.forum.response

import com.google.gson.annotations.SerializedName
import com.molohala.grow.data.comment.response.CommentResponse
import java.time.LocalDateTime

data class ForumResponse(
    @SerializedName("community")
    val forum: ForumContentResponse,
    val recentComment: CommentResponse?
) {
    companion object {
        fun dummy(
            liked: Boolean = true,
            recentComment: CommentResponse? = CommentResponse.dummy()
        ) = ForumResponse(
            forum = ForumContentResponse.dummy(liked),
            recentComment = recentComment
        )
    }
}

data class ForumContentResponse(
    @SerializedName("communityId")
    val forumId: Int,
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
        ) = ForumContentResponse(
            forumId = 0,
            content = "우와 지존입니다 ㅋ 응",
            createdAt = LocalDateTime.now(),
            like = 311,
            writerName = "이강현",
            writerId = 0,
            liked = liked
        )
    }
}
