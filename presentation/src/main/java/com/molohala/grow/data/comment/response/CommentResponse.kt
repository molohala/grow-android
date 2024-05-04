package com.molohala.grow.data.comment.response

import java.time.LocalDateTime

data class CommentResponse(
    val commentId: Int,
    val content: String,
    val createdAt: LocalDateTime,
    val memberId: Int,
    val name: String
) {
    companion object {
        fun dummy() =
            CommentResponse(
                commentId = 1,
                content = "안녕 정말 반갑습니다",
                createdAt = LocalDateTime.now(),
                memberId = 1,
                name = "이강현"
            )
    }
}