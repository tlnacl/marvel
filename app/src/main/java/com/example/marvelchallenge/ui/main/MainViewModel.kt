package com.example.marvelchallenge.ui.main

import com.example.marvelchallenge.network.MarvelApi
import com.example.marvelchallenge.uniflow.DataFlowBaseViewModel
import com.example.marvelchallenge.uniflow.data.ViewState
import com.example.marvelchallenge.network.Character
import com.example.marvelchallenge.uniflow.data.ViewEvent
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(private val marvelApi: MarvelApi) : DataFlowBaseViewModel() {
    fun loadCharacters() = action(
        onAction = {
            setState(ViewState.Loading)
            val characters = marvelApi.getCharacters().data.results
            setState(MainViewState(characters))
        },
        onError = { error, _ ->
            Timber.e(error, "MainViewModel Error")
            sendEvent(ViewEvent.ApiError(error.message ?: "Unknown Error"))
            setState(ViewState.Failed)
        }
    )

    data class MainViewState(val characters: List<Character>) : ViewState()
}