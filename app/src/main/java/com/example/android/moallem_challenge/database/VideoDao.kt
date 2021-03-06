package com.example.android.moallem_challenge.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * interface that works as a medium between ModelView class and the database
 */
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
    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param video new value to write
     */
    @Update
    suspend fun update(video: Video)
}
