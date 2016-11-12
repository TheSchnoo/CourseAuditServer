package web;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private final String code;          // CPSC
    private final int number;           // 210
    private final String name;          // Intermediate Computer Engineering
    private final String description;   // Aspects of developing apps using intermediate coding techniques
    private List<Course> prerequisites; // web.Course cpsc110
    private int credits;                // 3

    public Course(String code, int number, String name, String description, List<Course> prerequisites, int credits) {
        this.code = code;
        this.number = number;
        this.name = name;
        this.description = description;
        this.prerequisites = prerequisites;
        this.credits = credits;
    }

    public Course(String code, int number, String name, String description, int credits) {
        this.code = code;
        this.number = number;
        this.name = name;
        this.description = description;
        this.prerequisites = new ArrayList<>();
        this.credits = credits;
    }

    public String getCode(){
        return this.code;
    }

    public int getNumber(){
        return this.number;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public List<Course> getPrerequisites(){
        return this.prerequisites;
    }

    public void addPrerequisite(Course course){
        this.prerequisites.add(course);
    }

    public int getCredits(){
        return this.credits;
    }

}
