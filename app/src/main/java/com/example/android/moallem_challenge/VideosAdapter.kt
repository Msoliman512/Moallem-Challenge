package com.example.android.moallem_challenge

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.media.MediaMetadataRetriever
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.graphics.createBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.android.moallem_challenge.database.Video
import com.google.android.material.internal.ViewUtils
import com.google.android.material.internal.ViewUtils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * class documentation
 */
class VideosAdapter(
    private var videos: List<Video>,
    var videoClickListener: VideosAdapter.OnVideoClickListener,
    val context: Context,
    val test: Int
) :
    RecyclerView.Adapter<VideosAdapter.ViewHolder>() {
    var bitmapsMap: MutableMap<String?, Bitmap?> = mutableMapOf()

 init {

     println("Records: " + videos.count() + " " + test)
        // runBlocking {

        GlobalScope.launch(Dispatchers.IO) {            //launch
            try {
                println("forLoop starts Here: ")
                for ((index, value) in videos.withIndex()) {
                    println("forLoop Bitmaps: " + bitmapsMap.count() + " " + test)
                    bitmapsMap[value.url] = retrieveVideoFrameFromVideo(value.url + ".mp4")?.let {
                        Bitmap.createScaledBitmap(
                            it, dpToPx(context,200).toInt(), dpToPx(context,150).toInt(), false)
                    }//videos[position].url
                    println("forLoop video: " + index + " | " + value.subject + " | " + value.url)
                }
            } catch (throwable: RuntimeException) {
                throwable.printStackTrace()
            }
        }

        //  }
//        println("Bitmaps: " + bitmapsMap.count() + " " + test)
//        for ((index, value) in bitmapsMap.withIndex()) {
//            println("bitmap ${index} : " + value)
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.video, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var selectedBITMAP = createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        if (position < bitmapsMap.count())
            selectedBITMAP = bitmapsMap[videos[position].url] ?: selectedBITMAP
        val selectedVideo = videos[position]
        holder.thumbnail.setImageBitmap(getRoundedCornerBitmap(selectedBITMAP, dpToPx(context,15).toInt())?.let {
            overlay(
                it,BitmapFactory.decodeResource(context.resources,R.drawable.play))
        })  //bitmaps[position]
        holder.thumbnail.elevation = dpToPx(context,2).toFloat()
        holder.bind(selectedVideo, videoClickListener)
    }

    override fun getItemCount() = videos.count()

    /**
     * class documentation
     */
      class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageButton = itemView.findViewById(R.id.video_image_button)


        /**
         * function to attach a listener of type OnVideoClickListener to the Videos recyclerview items
         * @param video
         * @param action
         */
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
    /**
     * interface documentation
     */
    interface OnVideoClickListener {
        fun onVideoClick(video: Video)
    }

    fun update(filteredVideos: List<Video>) {
        videos = filteredVideos
        notifyDataSetChanged()
    }
    private fun getRoundedCornerBitmap(bitmap: Bitmap, pixels: Int): Bitmap? {
        val output = Bitmap.createBitmap(
            bitmap.width, bitmap
                .height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        val color = Color.RED
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        val roundPx = pixels.toFloat()
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }
    private fun overlay(bmp1: Bitmap, bmp2: Bitmap): Bitmap? {
        val bmOverlay =
            Bitmap.createBitmap(bmp1.width, bmp1.height, bmp1.config)
        val canvas = Canvas(bmOverlay)
        canvas.drawBitmap(bmp1, Matrix(), null)
        canvas.drawBitmap(bmp2,((bmp1.width/2) - bmp2.width/2).toFloat() , ((bmp1.height/2) - bmp2.height/2).toFloat(), null)
        return bmOverlay
    }
}