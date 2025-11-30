package com.example.setupapplication.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.setupapplication.data.model.Post
import com.example.setupapplication.data.remote.ApiService
import java.io.IOException

class PostPagingSource(
    private val api: ApiService
) : PagingSource<Int, Post>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize

            // JSONPlaceholder paging params
            val start = (page - 1) * pageSize

            val response = api.getPostsPaged(start, pageSize)

            val nextKey = if (response.isEmpty()) null else page + 1

            return LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextKey
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchor ->
            val page = state.closestPageToPosition(anchor)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }
}
