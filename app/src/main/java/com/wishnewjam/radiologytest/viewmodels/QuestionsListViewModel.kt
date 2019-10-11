package com.wishnewjam.radiologytest.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wishnewjam.radiologytest.QuestionsRepository
import com.wishnewjam.radiologytest.db.QuestionsEntity
import com.wishnewjam.radiologytest.ui.settings.Params
import com.wishnewjam.radiologytest.utilities.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel

class QuestionsListViewModel(private val trainRepository: QuestionsRepository) : ViewModel() {

    var paramsList: Params? = null

    var filter: MutableLiveData<String> = MutableLiveData("")

    var listPosition = MutableLiveData(0)

    var questions: LiveData<List<QuestionsEntity>> = Transformations.switchMap(filter) { input ->
        val params = paramsList
        if (params == null) {
            if (input.isNullOrEmpty()) {
                trainRepository.getAllQuestions()
            }
            else {
                trainRepository.getSearchQuestions("%$input%")
            }
        }
        else {
            if (input.isNullOrEmpty()) {
                trainRepository.getAllQuestionsWithParams(params)
            }
            else {
                trainRepository.getSearchQuestionsWithParams("%$input%", params)
            }
        }
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

    fun saveCurrentPosition(it: Int, context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit()
                .putInt(KEY_POSITION, it)
                .apply()
    }

    fun restorePosition(context: Context) {
        listPosition.value = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getInt(KEY_POSITION, 0)
    }

    companion object {
        const val PREFS_NAME = "scroll_state"
        const val KEY_POSITION = "position"
    }
}