package com.example.studybuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Course_RecyclerViewAdapter extends RecyclerView.Adapter<Course_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<CourseModel> courseModels;


    public Course_RecyclerViewAdapter(Context context, ArrayList<CourseModel> courseModels) {
        this.context = context;
        this.courseModels = courseModels;
    }

    @NonNull
    @Override
    public Course_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This inflates the layout and gives the view to each of our rows
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new Course_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Course_RecyclerViewAdapter.MyViewHolder holder, int position) {
        //assigning values to each of our views as they come back on the screen
        //based on the position of the recycler view

        holder.tvName.setText(courseModels.get(position).getCourseName());
        holder.tvID.setText(courseModels.get(position).getCourseID());
    }

    @Override
    public int getItemCount() {
        //recycler view keeping track of how many items you want displayed
        return courseModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //grabbing views from recycler_view_row layout file
        //kind of like the onCreate method

        TextView tvName, tvID;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.course_name);
            tvID = itemView.findViewById(R.id.course_id);
        }
    }
}
