package com.example.android.moallem_challenge

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.graphics.createBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.android.moallem_challenge.database.Video
import com.example.android.moallem_challenge.database.VideoDatabase
import kotlinx.coroutines.*


/**
 * class documentation
 */
class VideosAdapter(
    private val videos: List<Video>,
    var videoClickListener: VideosAdapter.OnVideoClickListener,
    val test: Int
) :
    RecyclerView.Adapter<VideosAdapter.ViewHolder>() {
     var bitmaps = mutableListOf<Bitmap?>()

    init {
        println("Records: " + videos.count() + " " + test)
       // runBlocking {

        GlobalScope.launch(Dispatchers.IO)  {            //launch
                try {
                    println("forLoop starts Here: ")
                    for ((index, value) in videos.withIndex()) {
                        println("forLoop Bitmaps: " + bitmaps.count() + " " + test)
                        bitmaps.add(
                            index,
                            retrieveVideoFrameFromVideo(value.url + ".mp4")
                        )//videos[position].url
                        println("forLoop video: " + index + " | " + value.subject + " | " + value.url)
                    }
                } catch (throwable: Throwable) {
                    throwable.printStackTrace()
                }
            }

      //  }
        println("Bitmaps: " + bitmaps.count() + " " + test)
        for ((index, value) in bitmaps.withIndex()) {
            println("bitmap ${index} : " + value)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.video, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var selectedBITMAP = createBitmap(100,100, Bitmap.Config.ARGB_8888)
        if(position < bitmaps.count())
         selectedBITMAP = bitmaps[position]!!
        val selectedVideo = videos[position]
        holder.thumbnail.setImageBitmap(selectedBITMAP)  //bitmaps[position]
        holder.bind(selectedVideo, videoClickListener)
    }

    override fun getItemCount() = videos.count()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageButton = itemView.findViewById(R.id.video_image_button)
        fun bind(video: Video, action: OnVideoClickListener) {

            //val emptyBITMAP = createBitmap(100,100, Bitmap.Config.ARGB_8888)
            //thumbnail.setImageBitmap(emptyBITMAP)  //bitmaps[position]
            thumbnail.setOnClickListener {
                action.onVideoClick(video)
            }
        }
    }

    @Throws(Throwable::class)
    fun retrieveVideoFrameFromVideo(videoPath: String?): Bitmap? {
        var bitmap: Bitmap? = null
        var mediaMetadataRetriever: MediaMetadataRetriever? = null
        try {
            mediaMetadataRetriever = MediaMetadataRetriever()
            mediaMetadataRetriever.setDataSource(
                videoPath,
                HashMap()
            )
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST)
        } catch (e: Exception) {
            e.printStackTrace()
            throw Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.message)
        } finally {
            mediaMetadataRetriever?.release()
        }
        return bitmap
    }

    interface OnVideoClickListener {
        fun onVideoClick(video: Video)
    }

}