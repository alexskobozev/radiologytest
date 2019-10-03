package com.wishnewjam.radiologytest.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuestionsEntity(
        @PrimaryKey var num: Int,
        @ColumnInfo(name = "question") var question: String?,
        @ColumnInfo(name = "var1") var var1: String?,
        @ColumnInfo(name = "var2") var var2: String?,
        @ColumnInfo(name = "var3") var var3: String?,
        @ColumnInfo(name = "var4") var var4: String?,
        @ColumnInfo(name = "var5") var var5: String?,
        @ColumnInfo(name = "answer") var answer: Int?,
        @ColumnInfo(name = "complexity") var complexity: Int?,
        @ColumnInfo(name = "themenumber") var themenumber: String?)