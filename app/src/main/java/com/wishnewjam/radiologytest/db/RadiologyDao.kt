package com.wishnewjam.radiologytest.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RadiologyDao {
    @Query("SELECT * FROM questionsentity ORDER BY rowId")
    fun getAll(): LiveData<List<QuestionsEntity>>

    @Query("SELECT * FROM questionsentity WHERE complexity IN (:complexity) AND themenumber IN (:themes)")
    fun getAllWithParams(complexity: List<Int>,
                         themes: List<String?>): LiveData<List<QuestionsEntity>>

    @Query("SELECT * FROM questionsentity WHERE question LIKE :search")
    fun getSearch(search: String): LiveData<List<QuestionsEntity>>

    @Query("SELECT * FROM questionsentity WHERE complexity IN (:complexity) AND themenumber IN (:themes) AND question LIKE :search")
    fun getSearchWithParams(search: String, complexity: List<Int>,
                            themes: List<String?>): LiveData<List<QuestionsEntity>>

    @Query("SELECT DISTINCT complexity FROM questionsentity ORDER BY complexity")
    fun fetchAllComplexeties(): LiveData<List<Int>>

    @Query("SELECT DISTINCT themenumber FROM questionsentity ORDER BY themenumber")
    fun fetchAllThemes(): LiveData<List<String?>>

    @Insert
    fun insertAll(vararg questionsEntity: QuestionsEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertQuestion(questionsEntity: QuestionsEntity): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateQuestion(questionsEntity: QuestionsEntity)

    @Delete
    fun delete(questionsEntity: QuestionsEntity)
}