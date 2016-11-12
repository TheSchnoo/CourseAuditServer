import org.junit.Before;
import org.junit.Test;
import web.Course;
import web.DatabaseController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Apathetic spawn of Wesb on 11/11/16.
 */
public class DatabaseControllerTest {

    private DatabaseController databaseController = new DatabaseController();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void shouldContainACourseWhenSelectingAllCourses() {
        assertNotNull(databaseController.selectAllCourses().get(0));
    }

    @Test
    public void shouldReturnPrerequisites() {
        Map<String,String> queryMap = new HashMap<>();
        queryMap.put("Code", "CPSC");
        queryMap.put("Number", "110");
        List<Course> courseList = databaseController.queryCourses(queryMap);
        List<Course> prerequisites = databaseController.getPrerequisites("CPSC",210);
        assertEquals(courseList.get(0).getName(), prerequisites.get(0).getName());
    }
}
