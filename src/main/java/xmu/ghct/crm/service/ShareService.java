package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.*;
import xmu.ghct.crm.dao.CourseDao;
import xmu.ghct.crm.dao.ShareDao;
import xmu.ghct.crm.dao.TeacherDao;
import xmu.ghct.crm.dao.TeamDao;
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
     * @param myCourseId
     * @param type
     * @return
     */
    public boolean deleteShare(BigInteger shareId,BigInteger myCourseId,int type) throws NotFoundException {
        //"共享分组"
        if(type==1) {
            return shareDao.deleteTeamShareByShareId(shareId,myCourseId);
        }//"共享讨论课"
        else if(type==2) {
            return shareDao.deleteSeminarShareByShareId(shareId,myCourseId);
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
                //删除从课程在klass_team中的全部记录

                //删除从课程下的所有team记录，删除从课程成员的team_student记录
                //更新从课程名单，存klass_team的关系，根据小组成员选课情况，判断klassId
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
