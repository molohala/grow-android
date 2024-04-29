package com.molohala.infinity.common.flow

sealed interface FetchFlow {
    data object Fetching: FetchFlow
    data object Success: FetchFlow
    data object Failure: FetchFlow
}