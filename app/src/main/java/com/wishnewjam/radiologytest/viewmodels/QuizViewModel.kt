package com.wishnewjam.radiologytest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wishnewjam.radiologytest.QuestionsRepository
import com.wishnewjam.radiologytest.db.QuestionsEntity
import com.wishnewjam.radiologytest.ui.settings.Params
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class QuizViewModel(val trainRepository: QuestionsRepository) : ViewModel() {

    var listOfQuestions: List<QuestionsEntity> = emptyList()
    val randomQuestion = MutableLiveData<QuestionsEntity>()
    val isAnswerRight: MutableLiveData<Boolean?> = MutableLiveData(null)

    var paramsList = MutableLiveData<Params>()

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    var allQuestions: LiveData<List<QuestionsEntity>> =
            Transformations.switchMap(paramsList) { input ->
                if (input == null) {
                    trainRepository.getAllQuestionsByKnowledge()
                }
                else {
                    trainRepository.getAllQuestionsWithParamsByKnow(input)
                }
            }

    fun randomizeQuestion() {
        isAnswerRight.value = null
        randomQuestion.value = listOfQuestions.first()
    }

    fun checkedAnswer(ans: Int) {
        randomQuestion.value?.let {
            val right = ans == it.answer
            isAnswerRight.postValue(right)
            if (right) {
                GlobalScope.launch {
                    trainRepository.updateKnowledge(it.num, it.knowledgeValue?.plus(1) ?: 1)
                }
            }
        }
    }

    fun init() {
        if (randomQuestion.value == null) {
            randomizeQuestion()
        }
    }
}