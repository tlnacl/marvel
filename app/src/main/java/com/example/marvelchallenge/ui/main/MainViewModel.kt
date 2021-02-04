package com.example.marvelchallenge.ui.main

import com.example.marvelchallenge.network.MarvelApi
import com.example.marvelchallenge.uniflow.DataFlowBaseViewModel
import com.example.marvelchallenge.uniflow.data.ViewState
import com.example.marvelchallenge.network.Character
import javax.inject.Inject

class MainViewModel @Inject constructor(private val marvelApi: MarvelApi) : DataFlowBaseViewModel(defaultState = ViewState.Loading) {
    fun loadCharacters() = action(
        onAction = {
            setState(MainViewState(characters = marvelApi.getCharacters().data.results))
        },
        onError = { error, _ ->
            error.printStackTrace()
            setState(ViewState.Failed) }
    )

    data class MainViewState(val characters: List<Character>) : ViewState()
}