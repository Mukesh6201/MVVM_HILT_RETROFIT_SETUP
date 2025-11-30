package com.example.setupapplication.domain.usecase

import com.example.setupapplication.data.model.Post
import com.example.setupapplication.data.repository.PostRepository
import com.example.setupapplication.utils.ResultState
import javax.inject.Inject
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow


class GetPostsUseCase @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke() = repository.getPosts()
}
