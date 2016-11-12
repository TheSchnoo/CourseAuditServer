package web;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wesb on 6/21/16.
 */
public class Schedule {

    private List<Course> pastCourses;
    private List<Course> futureCourses;
    private List<Course> requirements;

    public Schedule(List<Course> pastCourses, List<Course> futureCourses, List<Course> requirements) {
        this.pastCourses = pastCourses;
        this.futureCourses = futureCourses;
        this.requirements = requirements;
    }

    public Schedule(List<Course> pastCourses, List<Course> futureCourses) {
        this.pastCourses = pastCourses;
        this.futureCourses = futureCourses;
        this.requirements = new ArrayList<>();
    }

    public Schedule(List<Course> futureCourses) {
        this.pastCourses = new ArrayList<>();
        this.futureCourses = futureCourses;
        this.requirements = new ArrayList<>();
    }

    public Schedule() {
        this.pastCourses = new ArrayList<>();
        this.futureCourses = new ArrayList<>();
        this.requirements = new ArrayList<>();
    }

    public List<Course> getPastCourses(){
        return this.pastCourses;
    }

    public void addPastCourse(Course course){
        this.pastCourses.add(course);
    }

    public List<Course> getCourses(){
        return this.futureCourses;
    }

    public void addCourse(Course course){
        this.futureCourses.add(course);
    }

    public List<Course> getRequirements(){
        return this.requirements;
    }

    public void setRequirements(List<Course> requirements) {
        this.requirements = requirements;
    }

    public void addRequirement(Course requiredCourse) {
        requirements.add(requiredCourse);
    }

    public boolean hasRequiredPrerequisites(){
        for(Course course : futureCourses){
            if(!verifyPrequisitesOfAddedCourse(course)){
                return false;
            }
        }
        return true;
    }

    private boolean verifyPrequisitesOfAddedCourse(Course course){

        int prereqs = course.getPrerequisites().size()-1;
        int index = pastCourses.size()-1;

        while(prereqs > 0 && index > 0){
            if(course.getPrerequisites().contains(pastCourses.get(index))){
                prereqs--;
            }
            index--;
        }
        return prereqs <= 0;
    }

    public boolean meetsRequirements() {
        for(Course req: requirements) {
            if(!pastCourses.contains(req) && !futureCourses.contains(req)) {
                return false;
            }
        }
        return true;
    }
}
