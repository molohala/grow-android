package com.molohala.grow.data.comment.request

import com.google.gson.annotations.SerializedName

data class PatchCommentRequest(
    val content: String,
    @SerializedName("communityId")
    val forumId: Int
)
