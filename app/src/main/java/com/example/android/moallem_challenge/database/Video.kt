package com.example.android.moallem_challenge.database

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * data class that holds videos categories and urls
 */
@Entity(tableName = "videosTbl")
data class Video(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "subject") val subject: String?
)