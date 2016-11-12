package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebController {

    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Course> getCourses(@RequestParam(value = "code", required = false) String code,
                            @RequestParam(value = "number", required = false) Integer number) {
        DatabaseController databaseController = new DatabaseController();
        if (code == null && number == null) {
            return databaseController.selectAllCourses();
        } else if (number == null) {
            return databaseController.queryCourseByCode(code);
        } else if (code == null) {
            return  databaseController.queryCourseByNumber(number);
        } else {
            return databaseController.queryCourseByCode(code).stream()
                    .filter(x -> number == x.getNumber())
                    .collect(Collectors.toList());
        }
    }
}