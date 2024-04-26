package com.molohala.infinity.comment.request

data class PatchCommentRequest(
    val content: String,
    val communityId: Int
)
