package com.example.android.moallem_challenge

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.moallem_challenge.database.Video
import com.example.android.moallem_challenge.database.VideoDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    var subjects: ArrayList<Subject> = ArrayList()
    var videos: ArrayList<Video> = ArrayList()
    private var job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
     //   VideoDatabase.getInstance(this).videoDatabaseDao.insertMultiplevideos()
        videos.add(Video(1,"https://imgur.com/eegUQYj", "physics"))
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
        videos.add(Video(21,"https://imgur.com/Jh1dCjsh", "algebra"))
        videos.add(Video(22,"https://imgur.com/453JnCl", "literature"))
        videos.add(Video(23,"https://imgur.com/hJl9Zpg", "literature"))
        videos.add(Video(24,"https://imgur.com/SihTCFT", "literature"))
        videos.add(Video(25,"https://imgur.com/iorHMFg", "literature"))


        uiScope.launch {
            insert(videos)
            Toast.makeText(this@MainActivity, "Data Saved!", Toast.LENGTH_LONG).show()
        }


        window.decorView.setBackgroundColor(Color.WHITE)
        supportActionBar?.elevation = 0F
        subjects.add(Subject("Physics", R.drawable.physics))
        subjects.add(Subject("Biology", R.drawable.biology))
        subjects.add(Subject("History", R.drawable.history))
        subjects.add(Subject("Algebra", R.drawable.algebra))
        subjects.add(Subject("Literature", R.drawable.literature))
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL,false)
         recyclerView.adapter = SubjectsAdapter(subjects)

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    private suspend fun insert(videos: ArrayList<Video>) {
        withContext(Dispatchers.IO) {
            VideoDatabase.getInstance(this@MainActivity).videoDatabaseDao.insertMultiplevideos(videos)
        }
    }
}
