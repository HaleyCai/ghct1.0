package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.ShareRequestVO;
import xmu.ghct.crm.VO.ShareVO;
import xmu.ghct.crm.entity.Course;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.entity.Share;
import xmu.ghct.crm.mapper.CourseMapper;
import xmu.ghct.crm.mapper.KlassMapper;
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
    @Autowired
    KlassMapper klassMapper;
    /**
     * 获取已同意的，共享组队和共享讨论课信息
     * @param courseId
     * @return
     */
    public List<ShareVO> getAllSuccessShare(BigInteger courseId,String courseName,BigInteger teacherId)
    {
        List<ShareVO> all=new ArrayList<>();
        List<Share> allTeams=shareMapper.getAllTeamShare(courseId);
        all.addAll(shareToShareVO(allTeams,courseId,courseName,"共享分组",teacherId));
        List<Share> allSeminars=shareMapper.getAllSeminarShare(courseId);
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
            //“我”是主课程
            if(item.getMainCourseId().equals(courseId))
            {
                shareVO.setMyCourseType("主课程");
                shareVO.setOtherCourseId(item.getSubCourseId());
                shareVO.setOtherCourseName(courseMapper.getCourseByCourseId(shareVO.getOtherCourseId()).getCourseName());
                shareVO.setOtherTeacherName(courseDao.getTeacherNameByCourseId(shareVO.getOtherCourseId()));
            }
            else
            {
                shareVO.setMyCourseType("从课程");
                shareVO.setOtherCourseId(item.getMainCourseId());
                shareVO.setOtherCourseName(courseMapper.getCourseByCourseId(shareVO.getOtherCourseId()).getCourseName());
                shareVO.setOtherTeacherName(courseDao.getTeacherNameByCourseId(shareVO.getOtherCourseId()));
            }
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

    /**
     * 获取一条share的application信息
     * @param shareId
     * @return
     */
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

    public boolean deleteShareInCourse(BigInteger subCourseId,int type)
    {
        if(type==1)//修改从课程的course表中team_main_course_id为null
        {
            if(shareMapper.deleteTeamShareInCourse(subCourseId)>0)
                return true;
        }
        else//修改从课程的course表中seminar_main_course_id为null
        {
            if(shareMapper.deleteSeminarShareInCourse(subCourseId)>0)
                return true;
        }
        return false;
    }

    /**
     * 删除共享后，从课程下共享的队伍被删除
     * @param subCourseId
     * @return
     */
    public void deleteTeamWithKlass(BigInteger subCourseId)
    {
        List<Klass> klasses=klassMapper.listKlassByCourseId(subCourseId);
        for(Klass oneKlass:klasses)
        {
            klassMapper.deleteTeamWithKlass(oneKlass.getKlassId());
        }
    }

    public boolean deleteSeminarShareByShareId(BigInteger shareId)
    {
        if(shareMapper.deleteSeminarShareByShareId(shareId)>0)
            return true;
        else
            return false;
    }
}

