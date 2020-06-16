package com.example.android.moallem_challenge.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * class documentation
 */
@Database(entities = arrayOf(Video::class), version = 1, exportSchema = false)
abstract class VideoDatabase : RoomDatabase() {

    /**
     * Connects the VideoDatabase to the DAO.
     */
    abstract fun videoDao():VideoDao

    /**
     * Define a companion object, this allows us to add functions on the VideoDatabase class.
     *
     * For example, users can call `VideoDatabase.getInstance(context)` to instantiate
     * a new VideoDatabase.
     */
    companion object {

        @Volatile
        private var INSTANCE: VideoDatabase? = null

        /**
         * Helper function to get the database.
         *
         * If a database has already been retrieved, the previous database will be returned.
         * Otherwise, create a new database.
         *
         * This function is threadsafe, and callers should cache the result for multiple database
         * calls to avoid overhead.
         *
         * This is an example of a simple Singleton pattern that takes another Singleton as an
         * argument in Kotlin.
         *
         *
         * @param context The application context Singleton, used to get access to the filesystem.
         */
        fun getInstance(context: Context): VideoDatabase {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        VideoDatabase::class.java,
                        "subjects_videos_database"
                    ).build()
                }
                // Return instance; smart cast to be non-null.
                return INSTANCE as VideoDatabase

        }
    }
}