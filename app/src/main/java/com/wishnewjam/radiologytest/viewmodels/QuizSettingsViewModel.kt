package com.wishnewjam.radiologytest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.wishnewjam.radiologytest.QuestionsRepository
import com.wishnewjam.radiologytest.ui.settings.Param
import com.wishnewjam.radiologytest.ui.settings.Params
import com.wishnewjam.radiologytest.ui.settings.ParamsLiveData
import com.wishnewjam.radiologytest.utilities.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel

class QuizSettingsViewModel(trainRepository: QuestionsRepository) : ViewModel() {

    var paramsLiveData =
            ParamsLiveData(trainRepository.getAllComplexeties(), trainRepository.getAllThemes())
    var checkedParamsLiveData = MutableLiveData<Params>()

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    private val newDestination = MutableLiveData<Event<NavDirections>>()

    fun setNewDestination(destination: NavDirections) {
        newDestination.value = Event(destination)
    }

    fun getNewDestination(): LiveData<Event<NavDirections>> {
        return newDestination
    }

    fun setParamsForNavigation(params: List<Param>) {
        checkedParamsLiveData.value = Params(params.mapNotNull { param ->
            param.value.takeIf {
                param.type == Param.TYPE_COMPLEXITY && param.checked
            }
        }, params.mapNotNull { param ->
            param.value.takeIf {
                param.type == Param.TYPE_THEME && param.checked
            }
        })
    }
}
