package com.molohala.infinity.comment.response

import java.time.LocalDateTime

data class CommentResponse(
    val commentId: Int,
    val content: String,
    val createdAt: LocalDateTime,
    val memberId: Int,
    val name: String
)
