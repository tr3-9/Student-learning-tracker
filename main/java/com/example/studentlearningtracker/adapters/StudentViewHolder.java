package com.example.studentlearningtracker.adapters;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studentlearningtracker.R;

public class StudentViewHolder extends RecyclerView.ViewHolder {
    public final TextView tvRow;
    public StudentViewHolder(@NonNull View v) {
        super(v);
        tvRow = v.findViewById(R.id.tvRow);
    }
}
