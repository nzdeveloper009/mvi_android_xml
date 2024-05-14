package com.nokhaiz.android.mviexample.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nokhaiz.android.mviexample.data.repository.GetPostsRepository
import com.nokhaiz.android.mviexample.ui.main.intent.MainIntent
import com.nokhaiz.android.mviexample.ui.main.viewstate.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val getPostsRepository: GetPostsRepository) :
    ViewModel() {
    val mainIntent: Channel<MainIntent> = Channel(Channel.UNLIMITED)

    private val _state = MutableStateFlow<MainViewState>(MainViewState.Idle)
    val state: StateFlow<MainViewState>
        get() = _state


    init {
        handleIntent()
    }

    fun handleIntent() {
        viewModelScope.launch {
            mainIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.GetPosts -> fetchPosts()
                }
            }
        }
    }

    fun fetchPosts() {
        viewModelScope.launch {
            _state.value = MainViewState.Loading
            try {
                _state.value = MainViewState.Success(data = getPostsRepository.getPosts())
            } catch (e: Exception) {
                _state.value = MainViewState.Error(message = e.message.toString())
            }
        }
    }
}