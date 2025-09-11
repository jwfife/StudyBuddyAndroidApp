package com.example.studybuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Course_RecyclerViewAdapter extends RecyclerView.Adapter<Course_RecyclerViewAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<CourseModel> courseModels;
    private final String currentUserEmail;
    private final DatabaseHelper databaseHelper;
    private final OnCourseToggleListener listener;

    public Course_RecyclerViewAdapter(Context context, ArrayList<CourseModel> courseModels, String currentUserEmail, OnCourseToggleListener listener) {
        this.context = context;
        this.courseModels = courseModels;
        this.currentUserEmail = currentUserEmail;
        this.databaseHelper = new DatabaseHelper(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This inflates the layout and gives the view to each of our rows
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //assigning values to each of our views as they come back on the screen
        //based on the position of the recycler view
        CourseModel course = courseModels.get(position); //gets position of course
        //gets status of enrollment in current course
        boolean isEnrolled = databaseHelper.checkEnrollment(currentUserEmail, course.getCourseID());
        //adds course if enrolled
        holder.bind(course, isEnrolled);
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
        ToggleButton addCourse;

        public MyViewHolder(@NonNull View itemView, OnCourseToggleListener listener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.course_name);
            tvID = itemView.findViewById(R.id.course_id);
            addCourse = itemView.findViewById(R.id.toggleButton);

            addCourse.setOnClickListener(v -> {
                String courseID = tvID.getText().toString();
                String courseName = tvName.getText().toString();
                boolean isChecked = addCourse.isChecked();

                //delegates to listener
                listener.onCourseToggled(courseID, courseName, isChecked);
            });
        }

        public void bind(CourseModel model, boolean isEnrolled) {
            tvName.setText(model.getCourseName());
            tvID.setText(model.getCourseID());
            addCourse.setChecked(isEnrolled);
        }
    }

    public interface OnCourseToggleListener {
        void onCourseToggled(String courseId, String courseName, boolean isEnrolled);
    }
}
