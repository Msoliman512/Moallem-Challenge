package com.example.android.moallem_challenge.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VideoDao {
    /**
     * function to insert a list of records of type Video to the database
     * @param videos a list of videos used to house the data records that must be inserted to the database
     */
    @Insert
     suspend fun insertMultipleVideos(videos: List<Video>)
    /**
     * function to retrieve all records from the database
     */
    @Query("SELECT * FROM videosTbl")
     fun getAll(): LiveData<List<Video>>
    /**
     * function to retrieve a list of records of type Video according to their subject (filtered by subject field)
     * @param subjectName
     */
    @Query("SELECT * FROM videosTbl WHERE subject LIKE :subjectName")
    suspend fun findBySubject(subjectName: String): List<Video>
}
