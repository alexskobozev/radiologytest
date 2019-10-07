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
                updateKnowledge(it, 1)
            }
            else {
                updateKnowledge(it, -3)
            }
        }
    }

    private fun updateKnowledge(entity: QuestionsEntity, difference: Int) {
        GlobalScope.launch {
            var newValue = 0
            ((entity.knowledgeValue ?: 0) + difference).let {
                if (it > 0) newValue = it
            }
            trainRepository.updateKnowledge(entity.num, newValue)
        }
    }

    fun init() {
        if (randomQuestion.value == null) {
            randomizeQuestion()
        }
    }
}