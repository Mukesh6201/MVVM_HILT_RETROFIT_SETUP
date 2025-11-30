package com.example.setupapplication.data.remote

import com.example.setupapplication.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    /**
     * JSONPlaceholder supports _start and _limit for simple pagination:
     * GET /posts?_start=0&_limit=10
     */
    @GET("posts")
    suspend fun getPostsPaged(
        @Query("_start") start: Int,
        @Query("_limit") limit: Int
    ): List<Post>
}
