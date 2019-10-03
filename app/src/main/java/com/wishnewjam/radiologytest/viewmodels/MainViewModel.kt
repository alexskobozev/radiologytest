package com.wishnewjam.radiologytest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wishnewjam.radiologytest.utilities.Event

class MainViewModel : ViewModel() {
    private val newDestination = MutableLiveData<Event<Int>>()

    fun getNewDestination(): LiveData<Event<Int>> {
        return newDestination
    }

    fun setNewDestination(destinationId: Int) {
        newDestination.value = Event(destinationId)
    }
}