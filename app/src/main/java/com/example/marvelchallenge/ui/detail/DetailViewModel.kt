package com.example.marvelchallenge.ui.detail

import com.example.marvelchallenge.network.Character
import com.example.marvelchallenge.network.MarvelApi
import com.example.marvelchallenge.uniflow.DataFlowBaseViewModel
import com.example.marvelchallenge.uniflow.data.ViewEvent
import com.example.marvelchallenge.uniflow.data.ViewState
import retrofit2.HttpException
import javax.inject.Inject

class DetailViewModel  @Inject constructor(private val marvelApi: MarvelApi) : DataFlowBaseViewModel(defaultState = ViewState.Loading) {
    fun loadCharacter(characterId: Int) = action(
        onAction = {
            setState(ViewState.Loading)
            val character = marvelApi.getCharacter(characterId).data.results
            setState(DetailViewState(character[0]))
        },
        onError = { error, _ ->
            error.printStackTrace()
            if (error is HttpException || error is IllegalStateException) {
                sendEvent(ViewEvent.ApiError(error.message!!))
            }
            setState(ViewState.Failed)
        }
    )

    data class DetailViewState(val character: Character) : ViewState()
}