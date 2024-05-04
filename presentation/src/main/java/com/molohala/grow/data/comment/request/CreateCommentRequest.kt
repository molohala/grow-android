package com.molohala.grow.data.comment.request

import com.google.gson.annotations.SerializedName

data class CreateCommentRequest(
    val content: String,
    @SerializedName("communityId")
    val forum: Int
)
