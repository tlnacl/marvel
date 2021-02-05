package com.example.marvelchallenge.ui.main

import com.example.marvelchallenge.network.MarvelApi
import com.example.marvelchallenge.uniflow.DataFlowBaseViewModel
import com.example.marvelchallenge.uniflow.data.ViewState
import com.example.marvelchallenge.network.Character
import com.example.marvelchallenge.uniflow.data.ViewEvent
import retrofit2.HttpException
import javax.inject.Inject

class MainViewModel @Inject constructor(private val marvelApi: MarvelApi) : DataFlowBaseViewModel() {
    fun loadCharacters() = action(
        onAction = {
            setState(ViewState.Loading)
            val characters = marvelApi.getCharacters().data.results
            setState(MainViewState(characters))
        },
        onError = { error, _ ->
            error.printStackTrace()
            if (error is HttpException || error is IllegalStateException) {
                sendEvent(ViewEvent.ApiError(error.message!!))
            }
            setState(ViewState.Failed)
        }
    )

    data class MainViewState(val characters: List<Character>) : ViewState()
}