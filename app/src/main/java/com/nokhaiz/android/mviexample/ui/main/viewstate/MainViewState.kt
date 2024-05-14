package com.nokhaiz.android.mviexample.ui.main.viewstate

import com.nokhaiz.android.mviexample.data.model.FakeDTO

sealed class MainViewState {
    object Idle:MainViewState()
    object Loading: MainViewState()
    class Error(val message:String):MainViewState()
    class Success(val data:List<FakeDTO>):MainViewState()

}