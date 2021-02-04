package com.example.marvelchallenge.uniflow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.marvelchallenge.DispatcherProvider
import kotlinx.coroutines.withContext
import com.example.marvelchallenge.uniflow.data.Event
import com.example.marvelchallenge.uniflow.data.ViewEvent
import com.example.marvelchallenge.uniflow.data.ViewState

class LiveDataPublisher(defaultState: ViewState, val dispatcherProvider: DispatcherProvider) {
    private val _states = MutableLiveData<ViewState>()
    private val _events = MutableLiveData<Event<ViewEvent>>()

    //For immutable
    val states: LiveData<ViewState> = _states
    val events: LiveData<Event<ViewEvent>> = _events

    init {
        _states.value = defaultState
    }

    suspend fun publishState(state: ViewState) {
        withContext(dispatcherProvider.main()) {
            _states.value = state
        }
    }

    suspend fun publishEvent(event: ViewEvent) {
        withContext(dispatcherProvider.main()) {
            _events.value = Event(event)
        }
    }
}