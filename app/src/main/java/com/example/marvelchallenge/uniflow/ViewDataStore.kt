package com.example.marvelchallenge.uniflow

import com.example.marvelchallenge.uniflow.data.ViewData
import com.example.marvelchallenge.uniflow.data.ViewEvent
import com.example.marvelchallenge.uniflow.data.ViewState
import timber.log.Timber

class ViewDataStore(private val publisher: LiveDataPublisher, defaultState: ViewState) {

    var currentState: ViewState = defaultState
        private set

    suspend fun pushNewData(viewData: ViewData) {
        Timber.v("push -> $viewData")
        when (viewData) {
            is ViewState -> {
                currentState = viewData
                publisher.publishState(viewData)
            }
            is ViewEvent -> {
                publisher.publishEvent(viewData)
            }
        }
    }
}