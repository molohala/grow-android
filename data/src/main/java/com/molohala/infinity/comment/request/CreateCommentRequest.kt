package com.molohala.infinity.comment.request

data class CreateCommentRequest(
    val content: String,
    val communityId: Int
)
