package com.molohala.infinity.data.comment.request

data class CreateCommentRequest(
    val content: String,
    val communityId: Int
)
