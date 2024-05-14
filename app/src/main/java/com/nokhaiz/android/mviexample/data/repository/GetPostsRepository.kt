package com.nokhaiz.android.mviexample.data.repository

import com.nokhaiz.android.mviexample.data.api.APIService

class GetPostsRepository(private val apiService: APIService) {

    suspend fun getPosts() = apiService.getPosts()

}