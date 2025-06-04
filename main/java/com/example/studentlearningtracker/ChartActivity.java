package com.example.studentlearningtracker;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.studentlearningtracker.model.Student;
import com.example.studentlearningtracker.util.CsvLoader;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.*;

public class ChartActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_chart);

        setSupportActionBar(findViewById(R.id.toolbarChart));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //android default back btn

        BarChart chart = findViewById(R.id.barChart);
        Map<Student,Integer> map = CsvLoader.load(this);

        // bucket by learning style
        Map<String, List<Student>> buckets = new HashMap<>();
        for (Student s : map.keySet()) {
            buckets.computeIfAbsent(s.preferredLearningStyle, k -> new ArrayList<>()).add(s);
        }

        List<BarEntry> entries = new ArrayList<>();
        List<String> labels   = new ArrayList<>();
        int idx = 0;
        for (Map.Entry<String, List<Student>> e : buckets.entrySet()) {
            int sum = 0;  for (Student s : e.getValue()) sum += s.examScore;
            float avg = (float) sum / e.getValue().size(); //get average grade
            entries.add(new BarEntry(idx, avg));
            labels.add(e.getKey());
            idx++;
        }

        BarDataSet ds = new BarDataSet(entries, "Average Exam Score"); //label 1
        ds.setColors(ColorTemplate.MATERIAL_COLORS);
        ds.setValueTextSize(14f);
        chart.setData(new BarData(ds));

        chart.getDescription().setText("Learning Style vs Avg Score"); //label 2
        XAxis x = chart.getXAxis();
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setGranularity(1f); //AI helped with understanding setGranularity
        x.setValueFormatter(new IndexAxisValueFormatter(labels));

        chart.animateY(1000); //1000 is good time for chart to open
        chart.invalidate();
    }

    @Override public boolean onSupportNavigateUp() { finish(); return true; }
}
