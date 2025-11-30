package com.example.setupapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.setupapplication.R
import com.example.setupapplication.databinding.ActivityMainBinding
import com.example.setupapplication.ui.adapter.PostAdapter
import com.example.setupapplication.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()
    private lateinit var adapter: PostAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding correctly
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        binding.rvPosts.layoutManager = LinearLayoutManager(this)
        adapter = PostAdapter()
        binding.rvPosts.adapter = adapter

        // Show loader initially
        binding.progress.visibility = View.VISIBLE

        // Collect PagingData
        lifecycleScope.launch {
            viewModel.posts.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        // Hide loader when data is loaded
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                binding.progress.visibility =
                    if (loadStates.refresh is LoadState.Loading) View.VISIBLE else View.GONE
            }
        }
    }
}

