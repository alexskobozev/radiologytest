package com.wishnewjam.radiologytest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.wishnewjam.radiologytest.db.RadiologyDao
import com.wishnewjam.radiologytest.ui.settings.Param

class QuestionsRepository private constructor(private val radiologyDao: RadiologyDao) {

    fun getAllQuestions() = radiologyDao.getAll()

    fun getSearchQuestions(search: String) = radiologyDao.getSearch(search)

    fun getAllComplexeties() = radiologyDao.fetchAllComplexeties().switchMap { list ->
        MutableLiveData<List<Param>>(
                list.map { Param(Param.TYPE_COMPLEXITY, it?.toString() ?: "No") })
    }

    fun getAllThemes() = radiologyDao.fetchAllThemes().switchMap { list ->
        MutableLiveData<List<Param>>(list.map { Param(Param.TYPE_THEME, it ?: "No") })
    }

    companion object {
        @Volatile
        private var instance: QuestionsRepository? = null

        fun getInstance(plantDao: RadiologyDao) = instance ?: synchronized(this) {
            instance ?: QuestionsRepository(plantDao).also { instance = it }
        }
    }
}
