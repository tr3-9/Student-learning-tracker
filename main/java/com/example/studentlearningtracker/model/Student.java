package com.example.studentlearningtracker.model;

public class Student implements Comparable<Student> {

    public final String id;
    public final int age;
    public final String gender;
    public final int studyHoursPerWeek;
    public final String preferredLearningStyle;
    public final int onlineCoursesCompleted;
    public final int assignmentCompletionRate;
    public final int examScore;
    public final int attendanceRate;

    public Student(String[] row) {           // row length ≥ 10
        id                       = row[0].trim();
        age                      = parseInt(row[1]);
        gender                   = row[2].trim();
        studyHoursPerWeek        = parseInt(row[3]);
        preferredLearningStyle   = row[4].trim();
        onlineCoursesCompleted   = parseInt(row[5]);
        assignmentCompletionRate = parseInt(row[7]);
        examScore                = parseInt(row[8]);
        attendanceRate           = parseInt(row[9]);
    } //move array to student obj
    private int parseInt(String s) {
        if (s == null) return 0;
        return Integer.parseInt(s.replace("%", "").trim());
    }
    @Override public int compareTo(Student o) { return o.examScore - examScore; }
    @Override public int hashCode() { return id.hashCode(); }
    @Override public boolean equals(Object o) {
        return o instanceof Student && id.equals(((Student) o).id);
    }
}
