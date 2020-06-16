package com.example.android.moallem_challenge

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.android.moallem_challenge.database.Video
import com.example.android.moallem_challenge.database.VideoDatabase
import kotlinx.coroutines.*


/**
 * class documentation
 */
class VideosAdapter(private val videos: List<Video>, var clickListener: OnVideoListener, val test:Int) :
    RecyclerView.Adapter<VideosAdapter.ViewHolder>() {
    private var bitmaps = mutableListOf<Bitmap?>()
    init {
//
//        runBlocking {
//            launch {
//
//                //  holder.thumbnail.setImageBitmap(Bitmap.createBi)
//                try {
//                    for ((index, value) in videos.withIndex()) {
//                        bitmaps.add(
//                            index,
//                            retrieveVideoFrameFromVideo(value.url + ".mp4")
//                        )//videos[position].url
//                    }
//                } catch (throwable: Throwable) {
//                    throwable.printStackTrace()
//                }
//            }
//        }
println("Records: " + videos.count()+" " + test)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.video, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

         holder.thumbnail.setImageBitmap(retrieveVideoFrameFromVideo(videos[position].url + ".mp4"))  //bitmaps[position]
        holder.initialize(videos[position], clickListener)
    }
    override fun getItemCount() = videos.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val thumbnail: ImageButton = itemView.findViewById(R.id.video_image_button)
        fun initialize (video: Video, action:OnVideoListener){

            itemView.setOnClickListener{
                action.onVideoClick(video, adapterPosition)
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
     interface OnVideoListener {
        fun onVideoClick(video: Video, position: Int)
    }

}