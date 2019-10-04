package com.wishnewjam.radiologytest.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

class ParamsLiveData(complexities: LiveData<List<Param>>, themes: LiveData<List<Param>>) :
        MediatorLiveData<List<Param>>() {
    private var firstDivider = Param(Param.TYPE_DIVIDER, "Complexeties")
    private var secondDivider = Param(Param.TYPE_DIVIDER, "Themes")

    init {
        addSource(complexities) {
            value = combineLatestData(complexities, themes)
        }
        addSource(themes) {
            value = combineLatestData(complexities, themes)
        }
    }

    private fun combineLatestData(complexeties: LiveData<List<Param>>,
                                  themes: LiveData<List<Param>>): List<Param> {
        val combinedList = ArrayList<Param>()
        combinedList.add(firstDivider)
        complexeties.value?.let { combinedList.addAll(it) }
        combinedList.add(secondDivider)
        themes.value?.let { combinedList.addAll(it) }
        return combinedList
    }
}