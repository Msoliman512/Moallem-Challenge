package com.example.android.moallem_challenge

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    var subjects: ArrayList<Subject> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.setBackgroundColor(Color.WHITE)
        supportActionBar?.elevation = 0F
        subjects.add(Subject("Physics", R.drawable.physics))
        subjects.add(Subject("Biology", R.drawable.biology))
        subjects.add(Subject("History", R.drawable.history))
        subjects.add(Subject("Algebra", R.drawable.algebra))
        subjects.add(Subject("Literature", R.drawable.literature))
        recyclerView.layoutManager = LinearLayoutManager(this)
         recyclerView.adapter = SubjectsAdapter(subjects)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.overflow_menu, menu)
        return true
    }
}
