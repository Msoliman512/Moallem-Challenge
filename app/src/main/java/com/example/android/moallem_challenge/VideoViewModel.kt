package com.example.android.moallem_challenge

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.android.moallem_challenge.database.Video
import com.example.android.moallem_challenge.database.VideoDatabase

class VideoViewModel(application: Application): AndroidViewModel(application){
    private val db:VideoDatabase = VideoDatabase.getInstance(application)
    internal val allVideos : LiveData<List<Video>> = db.videoDao().getAll()

    suspend fun insertMultiple(videos: List<Video>){
        db.videoDao().insertMultipleVideos(videos)
    }

}