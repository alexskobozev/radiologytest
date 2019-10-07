package com.wishnewjam.radiologytest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wishnewjam.radiologytest.QuestionsRepository

class QuizViewModelFactory(private val repository: QuestionsRepository) :
        ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuizViewModel(repository) as T
    }
}