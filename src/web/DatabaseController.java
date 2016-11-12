package web;

import javax.xml.crypto.Data;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Apathetic spawn of Wesb on 11/11/16.
 */
public class DatabaseController {

    private Connection connection;
    private CourseConverter courseConverter;

    public DatabaseController() {
        this.courseConverter = new CourseConverter();
    }

    private void openConnection() {
        System.out.println(System.getenv("CLEARDB_DATABASE_URL"));
        try {
            URI dbUri = new URI(System.getenv("CLEARDB_DATABASE_URL"));
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
            connection = DriverManager.getConnection(dbUrl, username, password);
        } catch (Exception e) {
            System.out.println("Issue");
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Course> selectAllCourses() {
        openConnection();
        List<Course> courses = queryFromCourses("","");
        closeConnection();
        return courses;
    }

    public List<Course> queryCourseByCode(String code) {
        openConnection();
        code = code.toLowerCase();
        List<Course> courses = queryFromCourses("Code", code);
        closeConnection();
        return courses;
    }

    private List<Course> queryFromCourses(String filter, String value) {
        String query;
        if (Objects.equals(filter, "") || Objects.equals(value, "")) {
            query = "SELECT * FROM courses";
        } else {
            query = "SELECT * FROM courses WHERE + " + filter + "=" + value;
        }
        List<Course> courseList = null;
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            courseList = courseConverter.convertResultSetToCourseList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    private List<Course> queryFromCourses(Map<String,String> filters) {
        String query = "SELECT * FROM courses WHERE ";
        int index = 0;
        for(Map.Entry<String,String> filter : filters.entrySet()){
            if(index == 0) {
                query = query + filter.getKey() + "= '" + filter.getValue() + "'";
            } else {
                query = query + " AND " + filter.getKey() + "= '" + filter.getValue() + "'";
            }
            index++;
        }
        List<Course> courseList = null;
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            courseList = courseConverter.convertResultSetToCourseList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    private List<Course> queryFromPrerequisites(String code, int number) {
        String query;
        if (Objects.equals(code, "")) {
            query = "SELECT * FROM prerequisites";
        } else {
            query = "SELECT * FROM prerequisites WHERE courseCode='" + code + "' AND courseNumber='" + number + "'";
        }
        List<Course> courseList = null;
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            Map<String, String> queryMap = new HashMap<>();
            while(resultSet.next()) {
                queryMap.put("Code", resultSet.getString("prereqCode"));
                queryMap.put("Number", resultSet.getString("prereqNumber"));
            }
            courseList = queryFromCourses(queryMap);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseList;
    }

    public List<Course> queryCourseByNumber(int number) {
        return null;
    }

    public List<Course> getPrerequisites(String code, int number) {
        openConnection();
        List<Course> courses = queryFromPrerequisites(code, number);
        closeConnection();
        return courses;
    }

    public List<Course> queryCourses(Map<String,String> queryMap) {
        openConnection();
        List<Course> courses = queryFromCourses(queryMap);
        closeConnection();
        return courses;
    }

    public boolean addCourse(Course course) {

    }
}
