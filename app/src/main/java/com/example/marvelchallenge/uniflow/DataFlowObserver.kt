package com.example.marvelchallenge.uniflow

import androidx.lifecycle.LifecycleOwner
import com.example.marvelchallenge.uniflow.data.Event
import com.example.marvelchallenge.uniflow.data.ViewEvent
import com.example.marvelchallenge.uniflow.data.ViewState

/**
 * DataFlow Observers for states & events
 */

fun LifecycleOwner.onStates(vm: DataFlowBaseViewModel, handleStates: (ViewState) -> Unit) {
    vm.dataPublisher.states.observe(this, { state: ViewState? -> state?.let { handleStates(state) } })
}

//In case we need to peek event already handled
fun LifecycleOwner.onRawEvents(vm: DataFlowBaseViewModel, handleEvents: (Event<*>) -> Unit) {
    vm.dataPublisher.events.observe(this, { event -> event?.let { handleEvents(event) } })
}

fun LifecycleOwner.onEvents(vm: DataFlowBaseViewModel, handleEvents: (ViewEvent) -> Unit) {
    vm.dataPublisher.events.observe(this, { event -> event?.take()?.let { handleEvents(it) } })
}