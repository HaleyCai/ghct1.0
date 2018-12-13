package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.service.CourseService;

import java.text.ParseException;
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

}
