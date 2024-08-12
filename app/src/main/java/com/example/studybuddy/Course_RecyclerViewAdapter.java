package com.example.studybuddy;

import static com.example.studybuddy.SearchForClasses.getAddedCourses;

import android.app.Activity;
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
    Context context;
    ArrayList<CourseModel> courseModels;
    static ArrayList<String> addedCoursesStrings = new ArrayList<>();


    public Course_RecyclerViewAdapter(Context context, ArrayList<CourseModel> courseModels) {
        this.context = context;
        this.courseModels = courseModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This inflates the layout and gives the view to each of our rows
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //grabbing views from recycler_view_row layout file
        //kind of like the onCreate method

        ArrayList<CourseModel> courseModelsTest = new ArrayList<>();
        TextView tvName, tvID;
        ToggleButton addCourse;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.course_name);
            tvID = itemView.findViewById(R.id.course_id);
            addCourse = itemView.findViewById(R.id.toggleButton);

            itemView.setOnClickListener(this);
            addCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String fullCourseString = tvID.getText().toString() + " " + tvName.getText().toString();

                    if (addedCoursesStrings.contains(fullCourseString)) {
                        addedCoursesStrings.remove(fullCourseString);
                    }
                    else {
                        addedCoursesStrings.add(fullCourseString);
                    }
                    getAddedCourses(addedCoursesStrings);
//                    System.out.println("----START OF LOOP----");
//                    for (int i = 0; i < addedCoursesStrings.size(); i++){
//                        System.out.println(addedCoursesStrings.get(i));
//                    }
//                    System.out.println("----END OF LOOP----");
                }
            });

        }

        @Override
        public void onClick(View v) {

        }
    }

}
