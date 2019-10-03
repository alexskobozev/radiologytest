package com.wishnewjam.radiologytest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wishnewjam.radiologytest.QuestionsRepository
import com.wishnewjam.radiologytest.db.QuestionsEntity
import com.wishnewjam.radiologytest.utilities.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel

class QuestionsListViewModel(trainRepository: QuestionsRepository) : ViewModel() {

    var allQuestions: LiveData<List<QuestionsEntity>> = trainRepository.getAllQuestions()

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    private val newDestination = MutableLiveData<Event<Int>>()

    fun getNewDestination(): LiveData<Event<Int>> {
        return newDestination
    }

    fun setNewDestination(destinationId: Int) {
        newDestination.value = Event(destinationId)
    }
}