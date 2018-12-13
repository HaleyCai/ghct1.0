package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.entity.Course;
import xmu.ghct.crm.service.CourseService;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
public class CourseController {

    @Autowired
    CourseService courseService;

    @RequestMapping(value="/course",method = RequestMethod.POST)
    @ResponseBody
    public void creatCourse(@RequestBody Map<String,Object> courseMap) throws ParseException {
        courseService.creatCourse(courseMap);
    }

    @RequestMapping(value="/course",method = RequestMethod.GET)
    @ResponseBody
    public List<Course>  listCourseByTeacherId(BigInteger teacherId){
        return courseService.listCourseByTeacherId(teacherId);
    }

    @RequestMapping(value="/course/{courseId}",method = RequestMethod.GET)
    @ResponseBody
    public List<Course>  listCourseByCourseId(@PathVariable("courseId")BigInteger courseId){
        return courseService.listCourseByCourseId(courseId);
    }

    @RequestMapping(value="/course/{courseId}",method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteCourseByCourseId(@PathVariable("courseId")BigInteger courseId) {
        courseService.deleteCourseByCourseId(courseId);
    }
}
