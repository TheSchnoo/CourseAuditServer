package web;

import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Apathetic spawn of Wesb on 11/11/16.
 */
public class CourseConverter {

    public List<Course> convertResultSetToCourseList(ResultSet resultSet) {
        List<Course> courseList = new ArrayList<>();
        try {
            while(resultSet.next()){
                String code = resultSet.getString("Code");
                int number = resultSet.getInt("Number");
                String name = resultSet.getString("Name");
                String description = resultSet.getString("Description");
                int credits = resultSet.getInt("Credits");
                Course course = new Course(code, number, name, description, credits);
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseList;
    }
}
