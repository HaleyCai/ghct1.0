package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.ShareRequestVO;
import xmu.ghct.crm.VO.ShareVO;
import xmu.ghct.crm.entity.Course;
import xmu.ghct.crm.entity.Share;
import xmu.ghct.crm.mapper.CourseMapper;
import xmu.ghct.crm.mapper.ShareMapper;
import xmu.ghct.crm.mapper.TeacherMapper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class ShareDao {

    @Autowired
    ShareMapper shareMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    CourseDao courseDao;
    @Autowired
    TeacherMapper teacherMapper;
    /**
     * 获取已同意的，共享组队和共享讨论课信息
     * @param courseId
     * @return
     */
    public List<ShareVO> getAllSuccessShare(BigInteger courseId,String courseName,BigInteger teacherId)
    {
        List<ShareVO> all=new ArrayList<>();
        List<Share> allTeams=shareMapper.getAllTeamShare(courseId);
        System.out.println("allTeams"+allTeams);
        all.addAll(shareToShareVO(allTeams,courseId,courseName,"共享分组",teacherId));

        List<Share> allSeminars=shareMapper.getAllSeminarShare(courseId);
        System.out.println("allSeminars"+allSeminars);
        all.addAll(shareToShareVO(allSeminars,courseId,courseName,"共享讨论课",teacherId));
        return all;
    }

    public List<ShareVO> shareToShareVO(List<Share> shares,BigInteger courseId,String courseName,
                                        String shareType,BigInteger teacherId)
    {
        List<ShareVO> shareVOS=new ArrayList<>();
        for(Share item:shares)
        {
            ShareVO shareVO=new ShareVO();
            shareVO.setShareId(item.getShareId());
            shareVO.setShareType(shareType);
            shareVO.setCourseId(courseId);
            shareVO.setCourseName(courseName);
            if(item.getMainCourseId().equals(courseId))
                shareVO.setCourseType("主课程");
            else
                shareVO.setCourseType("从课程");

            //“我”是从课程教师，则otherTeacher为主课程教师
            if(item.getSubCourseTeacherId().equals(teacherId))
                shareVO.setOtherTeacherName(courseDao.getTeacherNameByCourseId(item.getMainCourseId()));
            else//“我”是主课程教师，则otherTeacher为从课程教师
                shareVO.setOtherTeacherName(courseDao.getTeacherNameByCourseId(item.getSubCourseId()));
            shareVO.setStatus(1);
            shareVOS.add(shareVO);
        }
        return shareVOS;
    }

    /**
     * 获取当前课程下，未处理状态的共享请求，本课程为从课程
     * @return
     */
    public List<ShareRequestVO> getUntreatedShare(BigInteger courseId,String courseName,BigInteger teacherId)
    {
        List<ShareRequestVO> all=new ArrayList<>();
        List<Share> allTeams=shareMapper.getTeamShareRequest(courseId);
        all.addAll(shareToShareRequestVO(allTeams,courseId,courseName,"共享分组申请",teacherId));
        List<Share> allSeminars=shareMapper.getSeminarShareRequest(courseId);
        all.addAll(shareToShareRequestVO(allSeminars,courseId,courseName,"共享讨论课申请",teacherId));
        return all;
    }

    public List<ShareRequestVO> shareToShareRequestVO(List<Share> shares,BigInteger courseId,String courseName,
                                                      String shareType,BigInteger teacherId)
    {
        List<ShareRequestVO> shareRequestVOS=new ArrayList<>();
        for(Share item:shares)
        {
            ShareRequestVO shareRequestVO=new ShareRequestVO();
            shareRequestVO.setShareId(item.getShareId());
            shareRequestVO.setShareType(shareType);
            shareRequestVO.setMainCourseId(item.getMainCourseId());
            Course mainCourse=courseDao.getCourseByCourseId(item.getMainCourseId());
            shareRequestVO.setMainCourseName(mainCourse.getCourseName());
            shareRequestVO.setMainTeacherName(teacherMapper.getTeacherById(mainCourse.getTeacherId()).getName());//获取主课程教师姓名
            //申请的从课程是自己的课程
            shareRequestVO.setSubCourseId(courseId);
            shareRequestVO.setSubCourseName(courseName);
            shareRequestVO.setSubTeacherId(teacherId);
            shareRequestVO.setStatus(-1);
            shareRequestVOS.add(shareRequestVO);
        }
        return shareRequestVOS;
    }




    public Share getTeamShareByShareId(BigInteger shareId)
    {
        return shareMapper.getTeamShareByShareId(shareId);
    }

    public Share getSeminarShareByShareId(BigInteger shareId)
    {
        return shareMapper.getSeminarShareByShareId(shareId);
    }

    public boolean deleteTeamShareByShareId(BigInteger shareId)
    {
        if(shareMapper.deleteTeamShareByShareId(shareId)>0)
            return true;
        else
            return false;
    }

    public boolean deleteSeminarShareByShareId(BigInteger shareId)
    {
        if(shareMapper.deleteSeminarShareByShareId(shareId)>0)
            return true;
        else
            return false;
    }
}

