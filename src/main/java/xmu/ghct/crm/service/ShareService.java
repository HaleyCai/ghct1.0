package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.CourseTeacherVO;
import xmu.ghct.crm.VO.ShareRequestVO;
import xmu.ghct.crm.VO.ShareTeamVO;
import xmu.ghct.crm.VO.ShareVO;
import xmu.ghct.crm.dao.CourseDao;
import xmu.ghct.crm.dao.ShareDao;
import xmu.ghct.crm.entity.Share;
import xmu.ghct.crm.exception.ClassNotFoundException;
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
    /**
     * 获取所有已同意的共享申请，查teacher的所有课程，再查课程的所有相关的某状态共享，不管是主还是从
     * @param teacherId
     * @return
     */
    public List<ShareVO> getAllSuccessShare(BigInteger teacherId){
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
        if(type.equals("共享分组"))
            return shareDao.deleteTeamShareByShareId(shareId);
        else if(type.equals("共享讨论课"))
            return shareDao.deleteSeminarShareByShareId(shareId);
        else
            return false;
    }

    public List<ShareRequestVO> getUntreatedShare(BigInteger teacherId){
        List<ShareRequestVO> shareRequestVOS=new ArrayList<>();
        List<CourseTeacherVO> courseTeacherVOS=courseDao.listCourseByTeacherId(teacherId);
        for(CourseTeacherVO item:courseTeacherVOS)
        {
            shareRequestVOS.addAll(shareDao.getUntreatedShare(item.getCourseId(),item.getCourseName(),teacherId));
        }
        return shareRequestVOS;
    }

}
