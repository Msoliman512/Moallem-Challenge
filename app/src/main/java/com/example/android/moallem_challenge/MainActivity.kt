package com.example.android.moallem_challenge

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.*
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.moallem_challenge.database.Video
import com.google.android.material.internal.ViewUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

const val VIDEO_URI_KEY = "VIDEO_URI"
/**
 * MainActivity
 *
 */
class MainActivity : BaseActivity(), VideosAdapter.OnVideoClickListener,
    SubjectsAdapter.OnSubjectIconClickListener {

    private lateinit var model: VideoViewModel

    var subjects: ArrayList<Subject> = ArrayList()
    lateinit var adapter: VideosAdapter

    private var videos: ArrayList<Video> = ArrayList<Video>()

    //private var retrievedVideos = mutableListOf<Video>()
    private var bitmaps = mutableListOf<Bitmap?>()


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Get the view model
        model = ViewModelProvider(this).get(VideoViewModel::class.java)
        videos_recyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        // Observe the model
        model.allVideos.observe(this, Observer { videos ->
            // Data bind the recycler view
            adapter = VideosAdapter(videos, this@MainActivity, this,4444)
 //           videos_recyclerView.adapter = adapter
//            Toast.makeText(
//                this@MainActivity,
//                "Records = " + videos.size,
//                Toast.LENGTH_SHORT
//            ).show()
        })
        //println("model.bitMaps: " + model.bitmapsMap.value)


        // populate the database for the first time
        /*   videos.add(Video(1,"https://imgur.com/eegUQYj", "physics"))
           videos.add(Video(2,"https://imgur.com/JxIxOQo", "physics"))
           videos.add(Video(3,"https://imgur.com/8KpS1Pu", "physics"))
           videos.add(Video(4,"https://imgur.com/Lt2hJB3", "physics"))
           videos.add(Video(5,"https://imgur.com/wJCNusT", "physics"))
           videos.add(Video(6,"https://imgur.com/8rsruId", "physics"))
           videos.add(Video(7,"https://imgur.com/NZUWEoi", "physics"))
           videos.add(Video(8,"https://imgur.com/OxqteMk", "physics"))
           videos.add(Video(9,"https://imgur.com/YwJas7u", "physics"))
           videos.add(Video(10,"https://imgur.com/jd9bJQn", "biology"))
           videos.add(Video(11,"https://imgur.com/OUZDoac", "biology"))
           videos.add(Video(12,"https://imgur.com/BHrz6Bz", "biology"))
           videos.add(Video(13,"https://imgur.com/PiLDNZy", "biology"))
           videos.add(Video(14,"https://imgur.com/Zzpn2Cj", "biology"))
           videos.add(Video(15,"https://imgur.com/LdEqUXy", "history"))
           videos.add(Video(16,"https://imgur.com/yEB6FVj", "history"))
           videos.add(Video(17,"https://imgur.com/ZFkIh2X", "history"))
           videos.add(Video(18,"https://imgur.com/qvsnkTk", "history"))
           videos.add(Video(19,"https://imgur.com/eB4EyEJ", "algebra"))
           videos.add(Video(20,"https://imgur.com/pnP5Y4h", "algebra"))
           videos.add(Video(21,"https://imgur.com/Jh1dCjs", "algebra"))      // old bug: https://imgur.com/Jh1dCjsh
           videos.add(Video(22,"https://imgur.com/453JnCl", "literature"))
           videos.add(Video(23,"https://imgur.com/hJl9Zpg", "literature"))
           videos.add(Video(24,"https://imgur.com/SihTCFT", "literature"))
           videos.add(Video(25,"https://imgur.com/iorHMFg", "literature"))  */


        // populate the database for the first time using coroutines or Update
//        launch {
//            baseContext?.let {
//                model.updateRecord(Video(21,"https://imgur.com/Jh1dCjs","algebra"))
//            }
//        }

        window.decorView.setBackgroundColor(Color.WHITE)
        supportActionBar?.elevation = 0F
        subjects.add(Subject("Physics", R.drawable.physics))
        subjects.add(Subject("Biology", R.drawable.biology))
        subjects.add(Subject("History", R.drawable.history))
        subjects.add(Subject("Algebra", R.drawable.algebra))
        subjects.add(Subject("Literature", R.drawable.literature))
        subjects_recyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        subjects_recyclerView.adapter = SubjectsAdapter(subjects, this)
//       val bitmap = retrieveVideoFrameFromVideo("https://imgur.com/iorHMFg.mp4")?.let {
//           Bitmap.createScaledBitmap(
//               it, ViewUtils.dpToPx(this,200).toInt(), ViewUtils.dpToPx(this, 150).toInt(), false)
//       };

//         test_image_button.setImageBitmap(bitmap?.let { getRoundedCornerBitmap(it,ViewUtils.dpToPx(this,15).toInt()) }?.let {
//             overlay(
//                 it,BitmapFactory.decodeResource(this@MainActivity.resources,
//                     R.drawable.play))
//         })
//        Toast.makeText(
//            this@MainActivity,
//            "records 2 =  ${retrievedVideos.count()}",
//            Toast.LENGTH_LONG
//        ).show() //0
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.overflow_menu, menu)
        return true
    }


    override fun onVideoClick(video: Video) {
     intent = Intent(this, VideoActivity::class.java).apply {
         putExtra(VIDEO_URI_KEY, video.url)
     }
     startActivity(intent)
//        Toast.makeText(
//            this,
//            video.subject + " thumbnail Clicked | url = " + video.url,
//            Toast.LENGTH_SHORT
//        ).show()
        //  println("Hello + $position")
    }

    override fun onSubjectIconClick(subject: Subject) {
        model.allVideos.observe(this, Observer { videos ->
            val filteredList = videos.filter { it.subject!!.toLowerCase(Locale.ROOT) == subject.name.toLowerCase(
                Locale.ROOT)
            }
            adapter.update(filteredList)
            videos_recyclerView.adapter = adapter
            println("filtered Videos for ${subject.name} : $filteredList")
        })
        //Toast.makeText(this, subject.name + " Icon Clicked" , Toast.LENGTH_SHORT).show()

    }
    /**
     * documentation
     */
    @Throws(Throwable::class)
    fun retrieveVideoFrameFromVideo(videoPath: String?): Bitmap? {
        val bitmap: Bitmap?
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



