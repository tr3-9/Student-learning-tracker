package com.example.studentlearningtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentlearningtracker.model.Student;
import com.example.studentlearningtracker.util.CsvLoader;
import com.example.studentlearningtracker.util.Statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

public class StudentDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_ID = "id";

    /** Conveient launcher from MainActivity */
    public static Intent launchIntent(Context c, String id) {
        return new Intent(c, StudentDetailsActivity.class).putExtra(EXTRA_ID, id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // toolbar and built in back arrow
        setSupportActionBar(findViewById(R.id.toolbarDetails));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //load all student and find the one requested
        Map<Student, Integer> data = CsvLoader.load(this);       // global rank map
        String id = getIntent().getStringExtra(EXTRA_ID);

        Student s = null;
        for (Student st : data.keySet()) {
            if (st.id.equalsIgnoreCase(id)) { s = st; break; }
        }
        if (s == null) { finish(); return; }

        //profile block
        String profile = String.format(Locale.US,
                "ID: %s\nAge: %d\nGender: %s\n" +
                        "Study hours/week: %d\nAssignment %%: %d\nExam score: %d\n" +
                        "Attendance %%: %d\nRank: %d",
                s.id, s.age, s.gender, s.studyHoursPerWeek,
                s.assignmentCompletionRate, s.examScore,
                s.attendanceRate, top10Rank(data, s));   // << rank fix

        ((TextView) findViewById(R.id.tvInfo)).setText(profile);

        // pearson r for the whole class
        ArrayList<Integer> hours  = new ArrayList<>();
        ArrayList<Integer> scores = new ArrayList<>();
        for (Student st : data.keySet()) {
            hours.add(st.studyHoursPerWeek);
            scores.add(st.examScore);
        }
        double r = Statistics.pearson(hours, scores);

        ((TextView) findViewById(R.id.tvCorr))
                .setText(String.format(Locale.US,
                        "Hours vs Score (all students): r = %.3f", r));
    }

    /** Returns 1-10 if the student sits in the current Top-10 slice, else their global rank. */
    private int top10Rank(Map<Student, Integer> map, Student target) {
        // copy keys and sort by score (Student.compareTo is score-based)
        ArrayList<Student> sorted = new ArrayList<>(map.keySet());
        Collections.sort(sorted);                          // highest → lowest

        for (int i = 0; i < sorted.size() && i < 10; i++) {
            if (sorted.get(i).equals(target)) return i + 1;   // 1-based Top-10 rank
        }
        // not in Top-10 meansfall back to stored global rank
        return map.getOrDefault(target, -1);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
