package com.molohala.infinity.common.flow

sealed class FetchFlow<Data> {
    class Fetching<Data>: FetchFlow<Data>()
    class Success<Data>(val data: Data): FetchFlow<Data>()
    class Failure<Data>: FetchFlow<Data>()
}