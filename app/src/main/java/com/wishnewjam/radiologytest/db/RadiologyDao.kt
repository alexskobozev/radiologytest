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

    @Insert
    fun insertAll(vararg questionsEntity: QuestionsEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertQuestion(questionsEntity: QuestionsEntity): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateQuestion(questionsEntity: QuestionsEntity)

    @Delete
    fun delete(questionsEntity: QuestionsEntity)
}