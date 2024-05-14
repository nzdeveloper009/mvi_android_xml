package com.nokhaiz.android.mviexample.data.api

import com.nokhaiz.android.mviexample.data.model.FakeDTO
import retrofit2.http.GET

interface APIService {

    @GET("posts")
    suspend fun getPosts():List<FakeDTO>
}