package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.*;
import xmu.ghct.crm.dao.CourseDao;
import xmu.ghct.crm.dao.ShareDao;
import xmu.ghct.crm.dao.TeacherDao;
import xmu.ghct.crm.entity.Course;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.entity.Share;
import xmu.ghct.crm.exception.ClassNotFoundException;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.mapper.ShareMapper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ShareService {
    @Autowired
    ShareDao shareDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    TeacherDao teacherDao;
    /**
     * 获取所有已同意的共享申请，查teacher的所有课程，再查课程的所有相关的某状态共享，不管是主还是从
     * @param teacherId
     * @return
     */
    public List<ShareVO> getAllSuccessShare(BigInteger teacherId) throws NotFoundException {
        List<ShareVO> allShare=new ArrayList<>();
        List<CourseTeacherVO> courseTeacherVOS=courseDao.listCourseByTeacherId(teacherId);
        for(CourseTeacherVO oneCourse:courseTeacherVOS)
        {
            allShare.addAll(shareDao.getAllSuccessShare(oneCourse.getCourseId(),oneCourse.getCourseName(),teacherId));
        }
        return allShare;
    }

    /**
     * 删除共享，删除记录
     * @param shareId
     * @param type
     * @return
     */
    public boolean deleteShare(BigInteger shareId,String type)
    {
        boolean success;
        if(type.equals("共享分组"))
        {
            Share share=shareDao.getTeamShareByShareId(shareId);
            success=shareDao.deleteTeamShareByShareId(shareId);
            //删除course表的关联记录
            success=shareDao.deleteShareInCourse(share.getSubCourseId(),1);
            //删除klass_team表的记录
            shareDao.deleteTeamWithKlass(share.getSubCourseId());
            return success;
        }
        else if(type.equals("共享讨论课"))
        {
            Share share=shareDao.getSeminarShareByShareId(shareId);
            success=shareDao.deleteSeminarShareByShareId(shareId);
            //删除course表的关联记录
            success=shareDao.deleteShareInCourse(share.getSubCourseId(),2);
            return success;
        }
        return false;
    }

    /**
     * 获得未处理的共享请求
     * @param teacherId
     * @return
     * @throws NotFoundException
     */
    public List<ShareRequestVO> getUntreatedShare(BigInteger teacherId) throws NotFoundException {
        List<ShareRequestVO> shareRequestVOS=new ArrayList<>();
        List<CourseTeacherVO> courseTeacherVOS=courseDao.listCourseByTeacherId(teacherId);
        for(CourseTeacherVO item:courseTeacherVOS)
        {
            shareRequestVOS.addAll(shareDao.getUntreatedShare(item.getCourseId(),item.getCourseName(),teacherId));
        }
        return shareRequestVOS;
    }

    /**
     * 教师获取自己受到的组队申请
     * @param teacherId
     * @return
     */
    public List<TeamApplicationVO> getUntreatedTeam(BigInteger teacherId) throws NotFoundException {
        return shareDao.getUntreatedTeamApplication(teacherId);
    }

    public boolean sentValidTeamRequest(TeamApplicationVO applicationVO)
    {
        return shareDao.launchTeamRequest(applicationVO);
    }

    public List<CourseWithTeacherVO> showSendCourse(BigInteger courseId) throws NotFoundException {
        List<CourseWithTeacherVO> allCourses=new ArrayList<>();
        List<CourseVO> courseVOS=courseDao.getAllCourse();
        for(CourseVO item:courseVOS)
        {
            CourseWithTeacherVO one=new CourseWithTeacherVO();
            one.setCourseId(item.getCourseId());
            one.setCourseNaem(item.getCourseName());
            one.setTeacherId(item.getTeacherId());
            one.setTeacherName(teacherDao.getTeacherNameByTeacherId(one.getTeacherId()));
            allCourses.add(one);
        }
        return allCourses;
    }
}
