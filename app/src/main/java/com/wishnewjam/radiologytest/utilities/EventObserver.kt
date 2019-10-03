package com.wishnewjam.radiologytest.utilities

import androidx.lifecycle.Observer

internal class EventObserver<T>(private val eventListener: EventListener<T>) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>) {
        val consumedValue = event.consume()
        eventListener.onEvent(consumedValue ?: return)
    }
}