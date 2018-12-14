package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.entity.*;
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
    public List<Course> listCourseByTeacherId(BigInteger teacherId){
        return courseService.listCourseByTeacherId(teacherId);
    }

    @RequestMapping(value="/course/{courseId}",method = RequestMethod.GET)
    @ResponseBody
    public List<Course> getCourseByCourseId(@PathVariable("courseId")BigInteger courseId){
        return courseService.getCourseByCourseId(courseId);
    }

    @RequestMapping(value="/course/{courseId}",method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteCourseByCourseId(@PathVariable("courseId")BigInteger courseId) {
        courseService.deleteCourseByCourseId(courseId);
    }

    @RequestMapping(value="/course/{courseId}/team",method = RequestMethod.GET)
    @ResponseBody
    public List<Team> getTeamMessageByCourseId(@PathVariable("courseId")BigInteger courseId){
        return courseService.getTeamMessageByCourseId(courseId);
    }

    @RequestMapping(value="/course/{courseId}/noTeam",method = RequestMethod.GET)
    @ResponseBody
    public List<User> getNoTeamStudentByCourseId(@PathVariable("courseId")BigInteger courseId){
        return courseService.getNoTeamStudentByCourseId(courseId);
    }

    @RequestMapping(value="/course/{courseId}/score",method = RequestMethod.GET)
    @ResponseBody
    public List<Score> listScoreByCourseId(@PathVariable("courseId")BigInteger courseId){
        return courseService.listScoreByCourseId(courseId);
    }

    @RequestMapping(value="/course/{courseId}/class",method = RequestMethod.GET)
    @ResponseBody
    public List<Klass> listKlassByCourseId(@PathVariable("courseId")BigInteger courseId){
        return courseService.listKlassByCourseId(courseId);
    }

    @RequestMapping(value="/course/{courseId}/class",method = RequestMethod.POST)
    @ResponseBody
    public void createKlass(@PathVariable("courseId")BigInteger courseId,@RequestBody Map<String,Object> klassMap)  {
        courseService.createKlass(courseId,klassMap);
    }

    @RequestMapping(value="/course/{courseId}/class/{classId}",method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteClassByCourseIdAndClassId(@PathVariable("courseId")BigInteger courseId,@PathVariable("classId")BigInteger classId){
        courseService.deleteClassByCourseIdAndClassId(courseId, classId);
    }

    @RequestMapping(value="/course/{courseId}/share",method = RequestMethod.GET)
    @ResponseBody
    public List<Share> getShareMessageByCourseId(@PathVariable("courseId")BigInteger courseId){
        return courseService.getShareMessageByCourseId(courseId);
    }

}