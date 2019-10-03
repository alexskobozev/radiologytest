package com.wishnewjam.radiologytest.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wishnewjam.radiologytest.QuestionsRepository
import com.wishnewjam.radiologytest.db.QuestionsEntity
import com.wishnewjam.radiologytest.utilities.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel

class QuestionsListViewModel(private val trainRepository: QuestionsRepository) : ViewModel() {

    var filter: MutableLiveData<String> = MutableLiveData("")

    var questions: LiveData<List<QuestionsEntity>> = Transformations.switchMap(filter) { input ->
        if (input.isNullOrEmpty()) {
            trainRepository.getAllQuestions()
        }
        else {
            trainRepository.getSearchQuestions("%$input%")
        }
    }

    init {
        Log.d("Some", "Some some")
    }

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

    fun searchForQuestion(query: String?) {
        filter.value = query
    }
}