package com.molohala.grow.data.comment.request

data class CreateCommentRequest(
    val content: String,
    val communityId: Int
)
