package com.example.studentlearningtracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentlearningtracker.adapters.StudentAdapter
import com.example.studentlearningtracker.model.Student
import com.example.studentlearningtracker.util.CsvLoader

class MainActivity : AppCompatActivity() {

    private lateinit var allData: Map<Student, Int>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        CsvLoader.printCsvToLogcat(this);
        // ^^^^ Uncomment this line to dump CSV file into logcat on startup
        setContentView(R.layout.activity_main)

        allData = CsvLoader.load(this)

        val top10 = allData.keys.sorted().take(10)

        val rv = findViewById<RecyclerView>(R.id.rvTop)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = StudentAdapter(top10) { s ->
            startActivity(StudentDetailsActivity.launchIntent(this, s.id)) //call activities upon app startup
        }

        findViewById<SearchView>(R.id.searchView)
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(q: String?): Boolean {
                    q?.let {
                        startActivity(
                            StudentDetailsActivity.launchIntent(
                                this@MainActivity, it.trim()
                            )
                        )
                    }
                    return true
                }
                override fun onQueryTextChange(newText: String?) = false
            })

        findViewById<Button>(R.id.btnCharts).setOnClickListener {
            startActivity(Intent(this, ChartActivity::class.java))
        }
    }
}
