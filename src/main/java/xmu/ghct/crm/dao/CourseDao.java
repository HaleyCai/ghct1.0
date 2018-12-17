package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.*;
import xmu.ghct.crm.mapper.CourseMapper;

import java.math.BigInteger;
import java.util.List;

@Component
public class CourseDao {

    @Autowired
    CourseMapper courseMapper;

    public void insertCourse(Course course){
        courseMapper.insertCourse(course);
    }

    public List<Course> listCourseByTeacherId(BigInteger teacherId){
        List<Course> courseList=courseMapper.listCourseByTeacherId(teacherId);
        if(courseList==null){
           //throw new CourseNotFindException();
        }
        return courseList;
    }

    public Course getCourseByCourseId(BigInteger courseId){
        Course course=courseMapper.getCourseByCourseId(courseId);
        if(course==null){
            //throw new CourseNotFindException();
        }
        return course;
    }

    public void deleteCourseByCourseId(BigInteger courseId){
        courseMapper.deleteCourseByCourseId(courseId);
    }

    public List<Team> getTeamInfoByCourseId(BigInteger courseId){
        List<Team> teamList=courseMapper.getTeamInfoByCourseId(courseId);
        if(teamList==null){
            //throw new TeamNotFindException();
        }
        return teamList;
    }

    public List<User> getNoTeamStudentByCourseId(BigInteger courseId){
        List<User> studentList=courseMapper.getNoTeamStudentByCourseId(courseId);
        if(studentList==null){
            //throw new NoStudentNotFindException();
        }
        return studentList;
    }

    public List<Score> listScoreByCourseId(BigInteger courseId){
        List<Score> scoreList=courseMapper.listScoreByCourseId(courseId);
        if(scoreList==null){
            //throw new NoStudentNotFindException();
        }
        return scoreList;
    }

    public List<Klass> listKlassByCourseId(BigInteger courseId){
        List<Klass> klassList=courseMapper.listKlassByCourseId(courseId);
        if(klassList==null){
            //throw new NoStudentNotFindException();
        }
        return klassList;
    }

    public void createKlass(BigInteger courseId,Klass klass){ courseMapper.createKlass(courseId,klass); }

    public void deleteClassByCourseIdAndClassId(BigInteger courseId,BigInteger classId){
        courseMapper.deleteClassByCourseIdAndClassId(courseId,classId);
    }

    public List<Share> getShareMessageByCourseId(BigInteger courseId){
        List<Share> shareList=courseMapper.getShareMessageByCourseId(courseId);
        if(shareList==null){
            //throw new ShareNotFindException();
        }
        return shareList;
    }

    public void deleteShareByCourseIdAndShareId(BigInteger courseId,BigInteger shareId){
        courseMapper.deleteShareByCourseIdAndShareId(courseId,shareId);
    }

    public void launchShareRequest(BigInteger courseId, Share share){ courseMapper.launchShareRequest(courseId,share); }

}
