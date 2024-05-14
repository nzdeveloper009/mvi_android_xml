package com.nokhaiz.android.mviexample.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.nokhaiz.android.mviexample.R
import com.nokhaiz.android.mviexample.databinding.ActivityMainBinding
import com.nokhaiz.android.mviexample.ui.main.adapter.MainAdapter
import com.nokhaiz.android.mviexample.ui.main.intent.MainIntent
import com.nokhaiz.android.mviexample.ui.main.viewmodel.MainViewModel
import com.nokhaiz.android.mviexample.ui.main.viewstate.MainViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel>()
    private val mainAdapter = MainAdapter()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        observeViewModel()
        lifecycleScope.launch {
            viewModel.mainIntent.send(MainIntent.GetPosts)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is MainViewState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is MainViewState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        mainAdapter.addItems(it.data)
                    }

                    is MainViewState.Error -> {
                        binding.progressBar.visibility = View.GONE
                    }

                    is MainViewState.Idle -> {}
                }
            }
        }
    }

    private fun initView() {
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.rvPost.adapter = mainAdapter
    }
}