package com.wishnewjam.radiologytest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wishnewjam.radiologytest.QuestionsRepository
import com.wishnewjam.radiologytest.db.QuestionsEntity
import com.wishnewjam.radiologytest.ui.settings.Params
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel

class QuizViewModel(trainRepository: QuestionsRepository) : ViewModel() {

    var listOfQuestions: List<QuestionsEntity> = emptyList()
    val randomQuestion = MutableLiveData<QuestionsEntity>()
    val isAnswerRight: MutableLiveData<Boolean?> = MutableLiveData(null)

    private var paramsList: Params? = null

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    var allQuestions: LiveData<List<QuestionsEntity>> = paramsList?.let {
        trainRepository.getAllQuestionsWithParams(it)
    } ?: run { trainRepository.getAllQuestions() }

    fun randomizeQuestion() {
        isAnswerRight.value = null
        randomQuestion.value = listOfQuestions.random()
    }

    fun checkedAnswer(ans: Int) {
        randomQuestion.value?.let {
            isAnswerRight.postValue(ans == it.answer)
        }
    }
}