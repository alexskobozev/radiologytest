package com.wishnewjam.radiologytest

import com.wishnewjam.radiologytest.db.RadiologyDao

class QuestionsRepository private constructor(private val radiologyDao: RadiologyDao) {

    fun getAllQuestions() = radiologyDao.getAll()

    fun getSearchQuestions(search: String) = radiologyDao.getSearch(search)

    companion object {
        @Volatile
        private var instance: QuestionsRepository? = null

        fun getInstance(plantDao: RadiologyDao) = instance ?: synchronized(this) {
            instance ?: QuestionsRepository(plantDao).also { instance = it }
        }
    }
}
