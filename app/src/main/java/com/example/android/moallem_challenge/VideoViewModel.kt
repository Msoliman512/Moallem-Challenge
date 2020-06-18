package com.example.android.moallem_challenge

import android.app.Application
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.moallem_challenge.database.Video
import com.example.android.moallem_challenge.database.VideoDatabase
import com.google.android.material.internal.ViewUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VideoViewModel(application: Application): AndroidViewModel(application){
    private val db:VideoDatabase = VideoDatabase.getInstance(application)
    internal val allVideos : LiveData<List<Video>> = db.videoDao().getAll()
    //internal val bitmapsMap: MutableLiveData<MutableMap<String?, Bitmap?>> = getBitmaps(allVideos)



    suspend fun insertMultiple(videos: List<Video>){
        db.videoDao().insertMultipleVideos(videos)
    }

    suspend fun updateRecord(video: Video)
    {
        db.videoDao().update(video)
    }

    @Suppress("UNCHECKED_CAST")
    private fun getBitmaps(videos: LiveData<List<Video>>) :MutableLiveData<MutableMap<String?, Bitmap?>> {
        var bitmapsMap: MutableMap<String?, Bitmap?> = mutableMapOf()
        GlobalScope.launch(Dispatchers.IO) {            //launch
            try {
                println("forLoop starts Here: ")
                for ((index, value) in videos.value?.withIndex()!!) {
                    println("forLoop Bitmaps: " + bitmapsMap.count() + " " )
                    bitmapsMap[value.url] = retrieveVideoFrameFromVideo(value.url + ".mp4")?.let {
                        Bitmap.createScaledBitmap(
                            it, ViewUtils.dpToPx(getApplication(), 200).toInt(), ViewUtils.dpToPx(
                                getApplication(),
                                150
                            ).toInt(), false)
                    }//videos[position].url
                    println("forLoop video: " + index + " | " + value.subject + " | " + value.url)
                }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            }
        }
        return bitmapsMap as MutableLiveData<MutableMap<String?, Bitmap?>>
    }

    /**
     *  documentation
     */
    @Throws(Throwable::class)
    fun retrieveVideoFrameFromVideo(videoPath: String?): Bitmap? {
        var bitmap: Bitmap?
        var mediaMetadataRetriever: MediaMetadataRetriever? = null
        try {
            mediaMetadataRetriever = MediaMetadataRetriever()
            mediaMetadataRetriever.setDataSource(
                videoPath,
                HashMap()
            )
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST)
        } catch (e: RuntimeException) { //Exception
            e.printStackTrace()
            throw Throwable("Exception in retrieve VideoFrameFromVideo(String videoPath)" + e.message)
        } finally {
            mediaMetadataRetriever?.release()
        }
        return bitmap
    }


}