package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.ShareRequestVO;
import xmu.ghct.crm.VO.ShareVO;
import xmu.ghct.crm.VO.TeamApplicationVO;
import xmu.ghct.crm.entity.Course;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.entity.Share;
import xmu.ghct.crm.entity.Team;
import xmu.ghct.crm.mapper.*;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.mapper.CourseMapper;
import xmu.ghct.crm.mapper.KlassMapper;
import xmu.ghct.crm.mapper.ShareMapper;
import xmu.ghct.crm.mapper.TeacherMapper;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    TeamMapper teamMapper;

    @Autowired
    TeamDao teamDao;
    /**
     * 获取已同意的，共享组队和共享讨论课信息
     * @param courseId
     * @return
     */
    public List<ShareVO> getAllSuccessShare(BigInteger courseId,String courseName,BigInteger teacherId) throws NotFoundException
    {
        List<ShareVO> all=new ArrayList<>();
        List<Share> allTeams=shareMapper.getAllTeamShare(courseId);
        all.addAll(shareToShareVO(allTeams,courseId,courseName,"共享分组",teacherId));
        List<Share> allSeminars=shareMapper.getAllSeminarShare(courseId);
        all.addAll(shareToShareVO(allSeminars,courseId,courseName,"共享讨论课",teacherId));
        return all;
    }

    public List<ShareVO> shareToShareVO(List<Share> shares,BigInteger courseId,String courseName,
                                        String shareType,BigInteger teacherId) throws NotFoundException
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
    public List<ShareRequestVO> getUntreatedShare(BigInteger courseId,String courseName,BigInteger teacherId) throws NotFoundException {
        List<ShareRequestVO> all=new ArrayList<>();
        List<Share> allTeams=shareMapper.getTeamShareRequest(courseId);
        all.addAll(shareToShareRequestVO(allTeams,courseId,courseName,1,teacherId));
        List<Share> allSeminars=shareMapper.getSeminarShareRequest(courseId);
        all.addAll(shareToShareRequestVO(allSeminars,courseId,courseName,2,teacherId));
        return all;
    }

    public List<ShareRequestVO> shareToShareRequestVO(List<Share> shares,BigInteger courseId,String courseName,
                                                      int shareType,BigInteger teacherId) throws NotFoundException {
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

    public Share getSubTeamShare(BigInteger courseId)
    {
        return shareMapper.getSubTeamShare(courseId);
    }

    public Share getSubSeminarShare(BigInteger courseId)
    {
        return shareMapper.getSubSeminarShare(courseId);
    }

    public boolean deleteTeamShareByShareId(BigInteger shareId,BigInteger courseId) throws NotFoundException {
        Share share=shareMapper.getTeamShareByShareId(shareId);
        if(shareMapper.deleteTeamShareByShareId(shareId)>0){
            //course从课程中的team_main置为null
            shareMapper.deleteTeamShareInCourse(share.getSubCourseId());
            //删除klass_team表的记录，team_student表的记录和team表
            deleteTeamWithCourse(share.getSubCourseId());
            return true;
        }
        else
            throw new NotFoundException("未找到该组队共享");
    }

    /**
     * 删除共享后，从课程下共享的队伍被删除
     * @param subCourseId
     * @return
     */
    public void deleteTeamWithCourse(BigInteger subCourseId) throws NotFoundException {
        List<Klass> klasses=klassMapper.listKlassByCourseId(subCourseId);
        if(klasses==null&&klasses.isEmpty())
        {
            throw new NotFoundException("未找到班级列表");
        }
        for(Klass oneKlass:klasses)
        {
            teamDao.deleteKlassTeam(oneKlass.getKlassId());
        }
    }

    public boolean deleteSeminarShareByShareId(BigInteger shareId,BigInteger myCourseId) throws NotFoundException {
        Share share=shareMapper.getSeminarShareByShareId(shareId);
        if(shareMapper.deleteSeminarShareByShareId(shareId)>0){
            //course从课程中的seminar_main置为null
            shareMapper.deleteSeminarShareInCourse(share.getSubCourseId());
            return true;
        }
        else
            throw new NotFoundException("未找到该讨论课共享");
    }

    public List<TeamApplicationVO> getUntreatedTeamApplication(BigInteger teacherId) throws NotFoundException {
        List<TeamApplicationVO> teamApplicationVOS=shareMapper.getUntreatedTeamApplication(teacherId);
        for(TeamApplicationVO oneTeam:teamApplicationVOS)
        {
            BigInteger teamId=oneTeam.getTeamId();
            BigInteger klassId=teamMapper.getKlassIdByTeamId(teamId);
            oneTeam.setKlassSerial(klassMapper.getKlassByKlassId(klassId).getKlassSerial());
            oneTeam.setTeamSerial(teamMapper.getTeamInfoByTeamId(teamId).getTeamSerial());
            oneTeam.setStatus(-1);//-1代表Null
        }
        return teamApplicationVOS;
    }

    /**
     * 处理非法组队申请，修改application表和team表
     * @param teamValidId
     * @param status
     * @return
     */
    public boolean dealTeamValidRequest(BigInteger teamValidId,int status)
    {
        TeamApplicationVO teamApplicationVO=shareMapper.getOneTeamApplication(teamValidId);
        int v1=shareMapper.dealTeamValidRequest(teamValidId, status);
        int v2=teamMapper.updateStatusByTeamId(teamApplicationVO.getTeamId(),status);
        if(v1>0 && v2>0)
            return true;
        return false;
    }

    /**
     * 发送共享请求
     * @param share
     * @return
     */
    public boolean launchShareRequest(Share share)
    {
        int v=0;
        if(share.getShareType().equals("共享组队")) {
            v = shareMapper.launchTeamShareRequest(share);
        }
        else {
            v = shareMapper.launchSeminarShareRequest(share);
        }
        if(v>0)
            return true;
        else
            return false;
    }

    /**
     * 发送非法组队请求
     * @param applicationVO
     * @return
     * @throws NotFoundException
     */
    public boolean launchTeamRequest(TeamApplicationVO applicationVO) throws NotFoundException
    {
        if(shareMapper.launchTeamRequest(applicationVO)>0)
            return true;
        else
            throw new NotFoundException("未找到request");
    }

    /**
     * 处理共享组队申请
     * @param shareId
     * @param status
     * @return
     */
    public boolean dealTeamShare(BigInteger shareId,int status)
    {
        if(shareMapper.updateTeamShareStatusByShareId(shareId, status)>0){
            if(status==1){//同意的话，还要修改从课程course的team_main
                Share share=shareMapper.getTeamShareByShareId(shareId);
                courseMapper.updateMainTeamByCourseId(share.getSubCourseId(),share.getMainCourseId());
            }
            return true;
        }
        else
            return false;
    }

    /**
     * 处理共享讨论课申请
     * @param shareId
     * @param status
     * @return
     */
    public boolean dealSeminarShare(BigInteger shareId,int status)
    {
        if(shareMapper.updateSeminarShareStatusByShareId(shareId,status)>0){
            if(status==1) {//同意的话，还要修改从课程course中seminar_main_
                Share share=shareMapper.getSeminarShareByShareId(shareId);
                courseMapper.updateMainSemianrByCourseId(share.getSubCourseId(),share.getMainCourseId());
            }
            return true;
        }
        else
            return false;
    }

    public boolean createSubCourseTeamList(BigInteger shareId) throws NotFoundException {
        Share share=shareMapper.getTeamShareByShareId(shareId);
        System.out.println("share "+share);
        BigInteger mainCourseId=share.getMainCourseId();
        BigInteger subCourseId=share.getSubCourseId();
        //删除从课程下的所有队伍
        deleteTeamWithCourse(subCourseId);

        //查主课程下，所有班级，所有班级下的所有队伍，
        List<BigInteger> teamIds=new ArrayList<>();
        List<Klass> mainCourseKlasses=klassMapper.listKlassByCourseId(mainCourseId);
        for(Klass oneKlass:mainCourseKlasses)
        {
            teamIds.addAll(teamMapper.listTeamIdByKlassId(oneKlass.getKlassId()));
        }
        //每个队伍下的每个成员都选了哪个班，一个小组的判断一次选最多班，作为新的klassId，存在表里
        int i=0;
        for(BigInteger teamId:teamIds)
        {
            List<BigInteger> studentIds=teamMapper.getStudentIdByTeamId(teamId);
            //根据studentId获得每个人在subCourse中的klassId，加入Map统计个数
            Map<BigInteger, Integer> klassIdMap = new TreeMap<>();
            for(BigInteger studentId:studentIds)
            {
                BigInteger klassId=klassMapper.getKlassIdByCourseIdAndStudentId(subCourseId,studentId);
                System.out.println("klassId=="+klassId);
                if(klassId!=null)
                {//该学生有选修从课程的课，加入，否则删去
                    if(klassIdMap.containsKey(klassId)) {
                        klassIdMap.put(klassId,klassIdMap.get(klassId)+1);
                    }
                    else{
                        klassIdMap.put(klassId,1);
                    }
                }
            }
            System.out.println("klassIdMap==="+klassIdMap);
            if(!klassIdMap.isEmpty())
            {//组内有人选该门课，则将team加入klass_team关系表，否则，不加入新的班级
                BigInteger subCourseKlassId=((TreeMap<BigInteger, Integer>) klassIdMap).lastKey();
                System.out.println("subCourseKlassId==="+subCourseKlassId);
                i+=teamMapper.insertKlassTeam(subCourseKlassId,teamId);
            }
        }
        if(i>0){
            return true;
        }
        else {
            return false;
        }
    }




}
