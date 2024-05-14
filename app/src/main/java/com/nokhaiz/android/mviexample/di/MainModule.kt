package com.nokhaiz.android.mviexample.di

import com.nokhaiz.android.mviexample.data.api.APIService
import com.nokhaiz.android.mviexample.data.repository.GetPostsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideApiService(): APIService =
        Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)

    @Singleton
    @Provides
    fun provideUserRepository(apiService: APIService): GetPostsRepository =
        GetPostsRepository(apiService)

}