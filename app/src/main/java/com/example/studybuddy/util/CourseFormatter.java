package com.example.studybuddy.util;

import java.util.List;

public class CourseFormatter {

    public static String formatCourses(List<String> courseIDs, List<String> courseTitles) {
        if (courseIDs == null || courseIDs.isEmpty()) {
            return "No courses yet";
        }

        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < courseIDs.size(); i++) {
            strBuilder.append(courseIDs.get(i))
                    .append(" ")
                    .append(courseTitles.get(i))
                    .append("\n");
        }
        return strBuilder.toString();
    }
}
