package com.example.android.moallem_challenge

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.android.moallem_challenge.database.Video
import com.example.android.moallem_challenge.database.VideoDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class MainActivity : BaseActivity(), VideosAdapter.OnVideoClickListener,
    SubjectsAdapter.OnSubjectIconClickListener {

    private lateinit var model: VideoViewModel

    var subjects: ArrayList<Subject> = ArrayList()

    private var videos: ArrayList<Video> = ArrayList<Video>()

    //private var retrievedVideos = mutableListOf<Video>()
    private var bitmaps = mutableListOf<Bitmap?>()


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
            videos_recyclerView.adapter = VideosAdapter(videos, this@MainActivity, 4444)
            Toast.makeText(
                this@MainActivity,
                "Records = " + videos.size,
                Toast.LENGTH_SHORT
            ).show()
        })


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

        // test_image_button.setImageBitmap( retrieveVideoFrameFromVideo("https://imgur.com/iorHMFg.mp4"))
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
//        retrievedVideos.get(position)
//        intent =Intent(this, VideoActivity::class.java)
//        startActivity(intent)
        Toast.makeText(this, video.subject +" thumbnail Clicked | url = "+ video.url, Toast.LENGTH_SHORT).show()
        //  println("Hello + $position")
    }

    override fun onSubjecticonClick(subject: Subject) {
        Toast.makeText(this, subject.name + " Icon Clicked", Toast.LENGTH_SHORT).show()
    }


}
