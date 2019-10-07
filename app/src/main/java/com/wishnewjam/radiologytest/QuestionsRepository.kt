package com.wishnewjam.radiologytest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.wishnewjam.radiologytest.db.RadiologyDao
import com.wishnewjam.radiologytest.ui.settings.Param
import com.wishnewjam.radiologytest.ui.settings.Params
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class QuestionsRepository private constructor(private val radiologyDao: RadiologyDao) {

    fun getAllQuestions() = radiologyDao.getAll()

    fun getAllQuestionsByKnowledge() = radiologyDao.getAllByKnow()

    fun getSearchQuestions(search: String) = radiologyDao.getSearch(search)

    fun getAllQuestionsWithParams(params: Params) =
            radiologyDao.getAllWithParams(params.complexities?.map { it.toInt() } ?: emptyList(),
                    params.themes ?: emptyList())

    fun getAllQuestionsWithParamsByKnow(params: Params) =
            radiologyDao.getAllWithParamsByKnow(params.complexities?.map { it.toInt() } ?:
            emptyList(),
                    params.themes ?: emptyList())

    fun getSearchQuestionsWithParams(search: String, params: Params) =
            radiologyDao.getSearchWithParams(search,
                    params.complexities?.map { it.toInt() } ?: emptyList(),
                    params.themes ?: emptyList())

    fun getAllComplexeties() = radiologyDao.fetchAllComplexeties().switchMap { list ->
        MutableLiveData<List<Param>>(list.map { Param(Param.TYPE_COMPLEXITY, it.toString()) })
    }

    fun getAllThemes() = radiologyDao.fetchAllThemes().switchMap { list ->
        MutableLiveData<List<Param>>(list.map { Param(Param.TYPE_THEME, it) })
    }

    suspend fun updateKnowledge(id: Int, newKnowledge: Int) {
        withContext(IO){
            radiologyDao.updateKnowledge(id,newKnowledge)
        }
    }

    companion object {
        @Volatile
        private var instance: QuestionsRepository? = null

        fun getInstance(plantDao: RadiologyDao) = instance ?: synchronized(this) {
            instance ?: QuestionsRepository(plantDao).also { instance = it }
        }
    }
}
