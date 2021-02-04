package com.example.marvelchallenge.uniflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelchallenge.AppCoroutineDispatcher
import com.example.marvelchallenge.DispatcherProvider
import com.example.marvelchallenge.uniflow.data.ViewState
import kotlin.reflect.KClass

abstract class DataFlowBaseViewModel(
    defaultState: ViewState = ViewState.Empty,
    val dispatcherProvider: DispatcherProvider = AppCoroutineDispatcher.dispatcher
) : ViewModel(), DataFlow {
    private val tag = this.toString()
    val dataPublisher: LiveDataPublisher = LiveDataPublisher(defaultState, dispatcherProvider)
    private val dataStore: ViewDataStore = ViewDataStore(dataPublisher, defaultState)
    private val actionDispatcher: ActionDispatcher
        get() = ActionDispatcher(viewModelScope, dataStore, this, dispatcherProvider, tag)

    final override fun getCurrentState() = actionDispatcher.getCurrentState()
    final override fun action(onAction: ActionFunction<ViewState>): ActionFlow = actionDispatcher.action(onAction)
    final override fun action(onAction: ActionFunction<ViewState>, onError: ActionErrorFunction): ActionFlow = actionDispatcher.action(onAction, onError)
    final override fun <T : ViewState> actionOn(stateClass: KClass<T>, onAction: ActionFunction<T>): ActionFlow = actionDispatcher.actionOn(stateClass, onAction)
    final override fun <T : ViewState> actionOn(stateClass: KClass<T>, onAction: ActionFunction<T>, onError: ActionErrorFunction): ActionFlow = actionDispatcher.actionOn(stateClass, onAction, onError)

}