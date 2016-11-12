package web;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

// TODO CLEAN DEPENDENCIES; ONLY NEED JUNIT AND HAMCREST

public class ScheduleTest {

    Course cpsc110;
    Course cpsc210;
    Course cpsc310;
    Schedule schedule;


    @Before
    public void setUp() throws Exception {
        List<Course> prerequisites210 = new ArrayList<>();
        List<Course> prerequisites310 = new ArrayList<>();
        cpsc110 = new Course("CPSC", 110, "Computation, Programs, and Programming", "Fundamental program and " +
                "computation structures. Introductory programming skills. Computation as a tool for information " +
                "processing, simulation and modeling, and interacting with the world.", 4);
        prerequisites210.add(cpsc110);
        prerequisites310.add(cpsc110);

        cpsc210 = new Course("CPSC", 210, "Software Construction", "", prerequisites210, 4);

        prerequisites310.add(cpsc210);

        cpsc310 = new Course("CPSC", 310, "Introduction to Software Engineering", "", prerequisites310, 4);

        List<Course> future210 = new ArrayList<>();
        future210.add(cpsc210);

        schedule = new Schedule(prerequisites210, future210);

    }

    @Test
    public void shouldReturnTrueWhenPrerequisuitesVerified() throws Exception {

        assertTrue(schedule.getPastCourses().get(0).equals(cpsc110));

        assertTrue(schedule.hasRequiredPrerequisites());
    }

    @Test
    public void shouldReturnFalseWhenPrerequisuitesNotFulfilled() throws Exception {

        assertEquals(schedule.getPastCourses().size(),1);

        schedule.addCourse(cpsc310);

        assertFalse(schedule.hasRequiredPrerequisites());
    }

    @Test
    public void shouldReturnTrueWhenRequirementsMetInPastCourses() throws Exception {

        assertEquals(schedule.getPastCourses().size(),1);

        assertTrue(schedule.meetsRequirements()); // Empty requirements

        schedule.addRequirement(cpsc110);

        assertTrue(schedule.meetsRequirements());
    }

    @Test
    public void shouldReturnTrueWhenRequirementsMetInFutureCourses() throws Exception {

        assertEquals(schedule.getPastCourses().size(), 1);
        assertEquals(schedule.getCourses().size(), 1);

        schedule.addRequirement(cpsc210);

        assertTrue(schedule.meetsRequirements());
    }

    @Test
    public void shouldReturnFalseWhenRequirementsNotMet() throws Exception {

        assertEquals(schedule.getPastCourses().size(), 1);
        assertEquals(schedule.getCourses().size(), 1);

        schedule.addRequirement(cpsc310);

        assertFalse(schedule.meetsRequirements());
    }

}