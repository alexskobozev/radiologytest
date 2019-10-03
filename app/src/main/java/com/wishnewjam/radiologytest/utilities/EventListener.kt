package com.wishnewjam.radiologytest.utilities

interface EventListener<T> {
    fun onEvent(t: T)
}
