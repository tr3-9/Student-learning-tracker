package com.example.studentlearningtracker.util;

import android.content.Context;
import android.util.Log;
import com.example.studentlearningtracker.model.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

//this file loads csv and dumps if nessesary
public final class CsvLoader {

    private static final String FILE_NAME = "students.csv";
    private static final String TAG       = "CsvLoader";

    // normal data loading for app
    public static Map<Student, Integer> load(Context ctx) {

        Map<Student, Integer> rankMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(ctx.getAssets().open(FILE_NAME)))) {

            br.readLine();                               // skip header
            List<Student> list = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",", -1);
                if (row.length >= 10) list.add(new Student(row));
            }
            Collections.sort(list);
            for (int i = 0; i < list.size(); i++) rankMap.put(list.get(i), i + 1);

            Log.d(TAG, "Parsed " + list.size() + " students"); //logcat 1

        } catch (IOException e) {
            Log.e(TAG, "CSV load failed", e);
        }
        return rankMap;
    }

//DEBUG METHOD
    public static void printCsvToLogcat(Context ctx) {
        final String DUMP_TAG = "CSV-DUMP";
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(ctx.getAssets().open(FILE_NAME)))) {

            String line;
            while ((line = br.readLine()) != null) {
                Log.d(DUMP_TAG, line);     // each row appears in Logcat
            }
            Log.d(DUMP_TAG, "---- End of " + FILE_NAME + " ----");

        } catch (IOException e) {
            Log.e(DUMP_TAG, "Unable to read " + FILE_NAME, e);
        }
    }


    private CsvLoader() { /* no instances */ }
}
