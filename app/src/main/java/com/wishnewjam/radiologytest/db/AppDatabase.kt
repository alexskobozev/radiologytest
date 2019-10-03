package com.wishnewjam.radiologytest.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wishnewjam.radiologytest.Constants.Companion.DATABASE_NAME

@Database(entities = [QuestionsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun trainDao(): RadiologyDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            val database = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .createFromAsset("databases/radiology.db")
                    .build()
            return database
        }
    }
}