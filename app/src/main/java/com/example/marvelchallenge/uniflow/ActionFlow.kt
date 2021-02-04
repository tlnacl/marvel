package com.example.marvelchallenge.uniflow

import com.example.marvelchallenge.uniflow.data.ViewEvent
import com.example.marvelchallenge.uniflow.data.ViewState

typealias ActionFunction<T> = suspend ActionFlow.(T) -> (Unit)
typealias ActionErrorFunction = suspend ActionFlow.(Exception, ViewState) -> (Unit)

class ActionFlow(
    val onAction: ActionFunction<ViewState>,
    val onError: ActionErrorFunction,
    private val dataStore: ViewDataStore
) {
    suspend fun setState(state: ViewState) {
        dataStore.pushNewData(state)
    }

    suspend fun setState(state: () -> ViewState) {
        dataStore.pushNewData(state())
    }

    suspend fun setStateAsync(state: suspend () -> ViewState) {
        dataStore.pushNewData(state())
    }

    suspend fun sendEvent(event: ViewEvent) {
        dataStore.pushNewData(event)
    }

    suspend fun sendEvent(event: () -> ViewEvent) {
        dataStore.pushNewData(event())
    }
}