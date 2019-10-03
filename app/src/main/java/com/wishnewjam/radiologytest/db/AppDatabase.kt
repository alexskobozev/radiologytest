package com.wishnewjam.radiologytest.db

import android.content.Context
import android.util.Log
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
            Log.d("Database", "start import, file is ${context.assets.list("databases")}")

            val database = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .createFromAsset("databases/radiology.db")
                    .build()
            Log.d("Database", "finish import")
//            GlobalScope.launch {
//                database.trainDao()
//                        .insertQuestion(
//                                QuestionsEntity(4000, "var1", "var1", "var1", "var1", "var1",
//                                        "var1", 1, 1, "3"))
//                database.trainDao()
//                        .insertQuestion(
//                                QuestionsEntity( 40001, "var2", "var2", "var1", "var1", "var1",
//                                        " var1", 1, 1, "3"))
//            }
            return database
        }
    }
}