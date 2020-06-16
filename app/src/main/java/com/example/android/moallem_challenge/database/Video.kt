package com.example.android.moallem_challenge.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videosTbl")
data class Video(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "subject") val subject: String?
)