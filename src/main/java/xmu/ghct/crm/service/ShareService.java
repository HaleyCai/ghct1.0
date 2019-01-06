package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.vo.*;
import xmu.ghct.crm.dao.CourseDao;
import xmu.ghct.crm.dao.ShareDao;
import xmu.ghct.crm.dao.TeacherDao;
import xmu.ghct.crm.entity.Course;
import xmu.ghct.crm.entity.Share;
import xmu.ghct.crm.exception.NotFoundException;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caiyq
 */
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
     * @param courseId
     * @return
     */
    public List<ShareVO> getAllSuccessShare(BigInteger courseId) throws NotFoundException {
        List<ShareVO> allShare=new ArrayList<>();
        Course course=courseDao.getCourseByCourseId(courseId);
        allShare.addAll(shareDao.getAllSuccessShare(course.getCourseId(),course.getCourseName(),course.getTeacherId()));
        return allShare;
    }

    /**
     * 删除共享，删除记录
     * @param shareId
     * @param type
     * @return
     */
    public boolean deleteShare(BigInteger shareId,int type) throws NotFoundException {
        //"共享分组"
        if(type==1) {
            return shareDao.deleteTeamShareByShareId(shareId);
        }//"共享讨论课"
        else if(type==2) {
            return shareDao.deleteSeminarShareByShareId(shareId);
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

    public boolean dealTeamValidRequest(BigInteger teamValidId,int status)
    {
        return shareDao.dealTeamValidRequest(teamValidId, status);
    }

    /**
     * 发送非法组队请求
     * @param applicationVO
     * @return
     * @throws NotFoundException
     */
    public boolean sentValidTeamRequest(TeamApplicationVO applicationVO) throws NotFoundException {
        return shareDao.launchTeamRequest(applicationVO);
    }


    public List<CourseWithTeacherVO> showSendCourse(BigInteger courseId) throws NotFoundException {
        List<CourseWithTeacherVO> allCourses=new ArrayList<>();
        List<CourseVO> courseVOS=courseDao.getAllCourse();
        for(CourseVO item:courseVOS)
        {
            if(!item.getCourseId().equals(courseId))
            {
                CourseWithTeacherVO one=new CourseWithTeacherVO();
                one.setCourseId(item.getCourseId());
                one.setCourseNaem(item.getCourseName());
                one.setTeacherId(item.getTeacherId());
                one.setTeacherName(teacherDao.getTeacherNameByTeacherId(one.getTeacherId()));
                allCourses.add(one);
            }
        }
        return allCourses;
    }

    public boolean launchShareRequest(Share share)
    {
        return shareDao.launchShareRequest(share);
    }

    /**
     * 处理共享（1组队+2讨论课）
     * @return
     */
    public boolean dealShare(BigInteger shareId,int type,int status) throws NotFoundException {
        //共享组队请求
        if(type==1) {
            boolean success=shareDao.dealTeamShare(shareId,status);
            if(status==1)
            {
                success=shareDao.createSubCourseTeamList(shareId);
            }
            return success;
        }//共享讨论课请求
        else if(type==2) {
            return shareDao.dealSeminarShare(shareId,status);
        }
        return false;
    }
}
