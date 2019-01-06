package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.vo.CourseLimitVO;
import xmu.ghct.crm.vo.CourseStudentVO;
import xmu.ghct.crm.vo.CourseTeacherVO;
import xmu.ghct.crm.vo.CourseVO;
import xmu.ghct.crm.vo.StudentCourseVO;
import xmu.ghct.crm.entity.*;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.mapper.*;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hzm
 */
@Component
public class CourseDao {

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    TeamMapper teamMapper;

    @Autowired
    ScoreMapper scoreMapper;

    @Autowired
    KlassMapper klassMapper;

    @Autowired
    ShareMapper shareMapper;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    StrategyMapper strategyMapper;



    public int insertCourse(CourseVO courseVO) throws SQLException {

        BigInteger isCourseId=courseMapper.getCourseIdByCourseNameAndTeacherId(courseVO.getCourseName(),courseVO.getTeacherId());
        if(isCourseId!=null)
        {
            throw new SQLException("该课程已存在");
        }
        int flag1=courseMapper.insertCourse(courseVO);
        int flag2=strategyMapper.insertMemberLimit(courseVO);
        int strategySerial=1;
        BigInteger id=strategyMapper.selectMaxIdFromTeamAndStrategy().add(new BigInteger("1"));
        strategyMapper.insertAndStrategy(id,"MemberLimitStrategy",courseVO.getMemberLimitId());
        List<CourseLimitVO> courseLimitVOS=courseVO.getCourseLimitVOS();
        if(courseLimitVOS!=null&&courseLimitVOS.size()>0){
            if(courseVO.getFlag()==true){
                System.out.println("strategy_id:"+id);
                for(CourseLimitVO item:courseLimitVOS){
                    strategyMapper.insertCourseMemberLimit(item);
                    strategyMapper.insertAndStrategy(id,"CourseMemberLimitStrategy",item.getCourseLimitId());
                }
            }
            else{
                BigInteger orId=strategyMapper.selectMaxIdFromTeamOrStrategy().add(new BigInteger("1"));
                for(CourseLimitVO item:courseLimitVOS){
                    strategyMapper.insertCourseMemberLimit(item);
                    strategyMapper.insertOrStrategy(orId,"CourseMemberLimitStrategy",item.getCourseLimitId());
                }
                strategyMapper.insertAndStrategy(id,"TeamOrStrategy",orId);
            }
        }

        strategyMapper.insertTeamStrategy(courseVO.getCourseId(),strategySerial,id,"TeamAndStrategy");
        strategySerial++;
        if(courseVO.getConflictCourseIdS()!=null&&courseVO.getConflictCourseIdS().size()>0){
            for(List<BigInteger> courseIdList:courseVO.getConflictCourseIdS()){
                if(courseIdList!=null&&courseIdList.size()>0){
                    BigInteger conflictCourseId=strategyMapper.selectMaxIdFromConflictCourseStrategy().add(new BigInteger("1"));
                    for(BigInteger courseIdItem:courseIdList){
                        strategyMapper.insertIntoConflictCourseStrategy(conflictCourseId,courseIdItem);
                    }
                    strategyMapper.insertTeamStrategy(courseVO.getCourseId(),strategySerial,conflictCourseId,"ConflictCourseStrategy");
                    strategySerial++;
                }
            }
        }
        return (flag1&flag2);
    }

    public List<CourseTeacherVO> listCourseByTeacherId(BigInteger teacherId){
        //根据teacherId查course
        List<CourseTeacherVO> courseTeachers = new ArrayList<>();
        List<Course> courses = courseMapper.listCourseByTeacherId(teacherId);
        if(courses==null||courses.isEmpty()){
           return null;
        }
        for (Course item : courses) {
            CourseTeacherVO courseTeacherVO = new CourseTeacherVO();
            courseTeacherVO.setCourseId(item.getCourseId());
            courseTeacherVO.setCourseName(item.getCourseName());
            courseTeachers.add(courseTeacherVO);
        }
        return courseTeachers;
    }

    public List<CourseStudentVO> listCourseByStudentId(BigInteger studentId) {
        List<CourseStudentVO> courseStudentVOList=courseMapper.listCourseByStudentId(studentId);
        if(courseStudentVOList==null||courseStudentVOList.isEmpty()){
            return null;
        }
        for(CourseStudentVO item:courseStudentVOList)
        {
            //根据CourseId查courseName
            Course course=courseMapper.getCourseByCourseId(item.getCourseId());
            item.setCourseName(course.getCourseName());
            //根据klassId查grade和klassSerial
            Klass klass=klassMapper.getKlassByKlassId(item.getKlassId());
            item.setGrade(klass.getGrade());
            item.setKlassSerial(klass.getKlassSerial());
        }
        return courseStudentVOList;
    }

    public Course getCourseByCourseId(BigInteger courseId) throws NotFoundException {
        Course course = courseMapper.getCourseByCourseId(courseId);
        if (course == null) {
            return null;
        }
        return course;
    }

    public int deleteCourseByCourseId(BigInteger courseId) throws NotFoundException {
        if(courseMapper.getCourseByCourseId(courseId)==null)
        {
            return 0;
        }
        return courseMapper.deleteCourseByCourseId(courseId);
    }

    public BigInteger getCourseIdByTeamId(BigInteger teamId) throws NotFoundException {
        BigInteger id=courseMapper.getCourseIdByTeamId(teamId);
        if(id==null)
        {
            return null;
        }
        return id;
    }

    public BigInteger getCourseIdByKlassId(BigInteger klassId) throws NotFoundException {
        BigInteger id=courseMapper.getCourseIdByKlassId(klassId);
        if(id==null)
        {
            return null;
        }
        return id;
    }

    public List<BigInteger> listCourseIdByTeacherId(BigInteger teacherId) throws NotFoundException {
        List<BigInteger> list = courseMapper.listCourseIdByTeacherId(teacherId);
        if (list == null && list.isEmpty()) {
            return null;
        }
        return list;
    }

    public List<BigInteger> listKlassIdByCourseId(BigInteger courseId) throws NotFoundException
    {
        List<BigInteger> list=courseMapper.listKlassIdByCourseId(courseId);
        if(list==null&&list.isEmpty())
        {
           return null;
        }
        return list;
    }

    public List<StudentCourseVO> listKlassStudentByStudentId(BigInteger studentId){
        return courseMapper.listKlassStudentByStudentId(studentId);
    }

    public String getTeacherNameByCourseId(BigInteger courseId) throws NotFoundException
    {
        BigInteger teacherId=courseMapper.getCourseByCourseId(courseId).getTeacherId();
        if(teacherId==null)
        {
            return null;
        }
        return teacherMapper.getTeacherById(teacherId).getName();
    }

    public List<CourseVO> getAllCourse() throws NotFoundException
    {
        List<CourseVO> courseVO=courseMapper.getAllCourse();
        if(courseVO==null&&courseVO.isEmpty())
        {
            return null;
        }
        return courseVO;
    }

    public List<Course> getCourseByTeacherId(BigInteger teacherId)
    {
        return courseMapper.getCourseByTeacherId(teacherId);
    }
}