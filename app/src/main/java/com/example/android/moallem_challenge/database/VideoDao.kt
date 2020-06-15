package com.example.android.moallem_challenge.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VideoDao {
    @Insert
    fun insert(video: Video)

    @Insert
    fun insertMultiplevideos(videos: List<Video>)

    @Query("SELECT * FROM video")
    fun getAll(): List<Video>

    @Query("SELECT * FROM video WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Video>

    @Query("SELECT * FROM video WHERE subject LIKE :subjectName")
    fun findBySubject(subjectName: String): List<Video>
}
