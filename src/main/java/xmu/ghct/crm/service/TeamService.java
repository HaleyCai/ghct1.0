package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.*;
import xmu.ghct.crm.dao.KlassDao;
import xmu.ghct.crm.dao.StrategyDao;
import xmu.ghct.crm.dao.StudentDao;
import xmu.ghct.crm.dao.TeamDao;
import xmu.ghct.crm.entity.Team;
import xmu.ghct.crm.entity.User;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    TeamDao teamDao;

    @Autowired
    StudentDao studentDao;

    @Autowired
    KlassDao klassDao;

    @Autowired
    StrategyDao strategyDao;
    /**
     * @cyq
     * 查课程下所有队伍的简单信息
     * @param courseId
     * @return
     */
    public List<TeamSimpleInfo> listTeamByCourseId(BigInteger courseId) {
        BigInteger mainCourse=getTeamMainCourseId(courseId);
        List<Team> teamList=teamDao.listTeamInfoByCourseId(mainCourse);
        return teamTOTeamSimpleInfo(teamList);
    }

    /**
     * @cyq
     * 判断一个课程是否为共享组队主课程
     * @return
     */
    public BigInteger getTeamMainCourseId(BigInteger courseId)
    {
        //*******查表，看courseId是从课程还是主课程，是主课程或未共享分组，则根据该courseId查询
        List<ShareTeamVO> shareTeamVOs=teamDao.getShareTeamInfoByCourseId(courseId);
        System.out.println("shareTeamVO"+shareTeamVOs);
        for(ShareTeamVO shareTeamItem:shareTeamVOs)
        {
            if(courseId.equals(shareTeamItem.getSubCourseId()))
                return shareTeamItem.getMainCourseId();
        }
        return courseId;
    }

    /**
     * @cyq
     * Team对象的list转为TeamSimpleInfo的list
     * @param teamList
     * @return
     */
    public List<TeamSimpleInfo> teamTOTeamSimpleInfo(List<Team> teamList)
    {
        List<TeamSimpleInfo> teamSimpleInfoList=new ArrayList<>();
        for(Team teamItem:teamList){
            TeamSimpleInfo teamSimpleInfo=new TeamSimpleInfo();
            teamSimpleInfo.setTeamId(teamItem.getTeamId());
            teamSimpleInfo.setTeamName(teamItem.getTeamName());
            //队伍的序号是班号-队伍号
            //根据klassId查klassSerial
            int klassSerial=klassDao.getKlassByKlassId(teamItem.getKlassId()).getKlassSerial();
            teamSimpleInfo.setTeamSerial(String.valueOf(klassSerial)+"-"+String.valueOf(teamItem.getTeamSerial()));
            teamSimpleInfo.setStatus(teamItem.getStatus());
            teamSimpleInfoList.add(teamSimpleInfo);
        }
        return teamSimpleInfoList;
    }

    /**
     * @cyq
     * 某一队伍的信息，包括简单信息和组长、成员信息
     * @param teamId
     * @return
     */
    public TeamInfoVO getTeamByCourseId(BigInteger teamId){
        TeamInfoVO teamInfoVO=new TeamInfoVO();
        Team team=teamDao.getTeamInfoByTeamId(teamId);
        teamInfoVO.setTeamId(team.getTeamId());
        teamInfoVO.setTeamName(team.getTeamName());
        teamInfoVO.setTeamSerial(team.getTeamSerial());
        teamInfoVO.setKlassId(team.getKlassId());
        teamInfoVO.setKlassSerial(team.getKlassSerial());
        teamInfoVO.setStatus(team.getStatus());
        //查询组长信息
        StudentVO studentVO=new StudentVO();
        User leader=studentDao.getStudentByStudentId(team.getLeaderId());
        studentVO.setAccount(leader.getAccount());
        studentVO.setEmail(leader.getEmail());
        studentVO.setName(leader.getName());
        studentVO.setStudentId(leader.getId());
        studentVO.setTeamId(leader.getTeamId());
        teamInfoVO.setTeamLeader(studentVO);
        teamInfoVO.setTeamLeader(studentVO);
        //查询组员信息
        List<BigInteger> studentIdList=teamDao.getStudentIdByTeamId(teamId);
        List<StudentVO> members=new ArrayList<>();
        for(BigInteger studentIdItem:studentIdList){
            if(studentIdItem.equals(team.getLeaderId()))
                continue;
            User student=studentDao.getStudentByStudentId(studentIdItem);
            StudentVO member=new StudentVO();
            member.setName(student.getName());
            member.setStudentId(student.getId());
            member.setEmail(student.getEmail());
            member.setAccount(student.getAccount());
            member.setTeamId(student.getTeamId());
            members.add(member);
        }
        teamInfoVO.setMembers(members);
        return teamInfoVO;
    }

    /**
     * @cyq
     * 获取课程里未组队学生
     * @param courseId
     * @return
     */
    public List<StudentVO> getNoTeamStudentByCourseId(BigInteger courseId) {
        return teamDao.getNoTeamStudentByCourseId(courseId);
    }

    /**
     * @cyq
     * 级联删除队伍
     * @param teamId
     * @return
     */
    public boolean deleteTeam(BigInteger teamId)
    {
        return teamDao.deleteTeam(teamId);
    }

    /**
     * @cyq
     * 退出队伍、移除成员
     * @param teamId
     * @param studentId
     * @return
     */
    public boolean removeTeamMember(BigInteger teamId,BigInteger studentId)
    {
        //*******用jwt中的id判断操作人是否是该队伍组长，若是，继续删除，若不是返回删除失败
        //删除操作，判断删除的student是否是组长，是则解散小组，否则删除成员
        Team team=teamDao.getTeamInfoByTeamId(teamId);
        if(team.getLeaderId()==studentId)
            return teamDao.deleteTeam(teamId);
        else
            return teamDao.removeTeamMember(teamId,studentId);
    }

    /**
     * 加入小组成员，先查有是否超过人数，再加入，再判断当前队伍状态
     * @param teamId
     * @param studentIds
     * @return
     */
    public boolean addTeamMember(BigInteger teamId,List<BigInteger> studentIds)
    {
        boolean success=false;
        for(BigInteger id:studentIds)
        {
            //if(team的人数已满，不能加入)，写在这还是写在判断组队是否合法处？？？
            success=success&&teamDao.addTeamMember(teamId,id);
        }
        //判断并修改组队状态是否合法
        return success;
    }

    /**
     * @cyq
     * 根据班级，判断组队是否合法并修改
     * @param teamId
     * @param courseId
     * @return
     */
    public void judgeTeamValid(BigInteger teamId,BigInteger courseId)
    {
    }


    /**
     * @author hzm
     * 根据teamId获得klassId
     * @param teamId
     * @return
     */
    public BigInteger getKlassIdByTeamId(BigInteger teamId){
        return teamDao.getKlassIdByTeamId(teamId);
   }

    /**
     * 判断队伍有效性
     * @param teamId
     * @return
     */
    public boolean judgeIllegal(BigInteger teamId){
        BigInteger courseId=teamDao.getCourseIdByTeamId(teamId);
        List<BigInteger> studentIdList=strategyDao.listStudentIdByTeamId(teamId);
        List<TeamStrategyVO> teamStrategyVOList=strategyDao.listTeamStrategyByCourseId(courseId);
        for(TeamStrategyVO item:teamStrategyVOList){
            if(item.getStrategyName().equals("MemberLimitStrategy")){
                System.out.println("MemberLimitStrategy");
                int teamMemberNumber=strategyDao.getTeamMemberNumber(teamId);
                CourseVO courseVO=strategyDao.getTeamMemberLimit(item.getStrategyId());
                if(teamMemberNumber>courseVO.getMaxMember()||teamMemberNumber<courseVO.getMinMember())return false;
            }
            else if(item.getStrategyName().equals("TeamAndStrategy")){
                System.out.println("TeamAndStrategy");
                List<AndOrOrStrategyVO> andOrOrStrategyVOS=strategyDao.selectAndStrategy(item.getStrategyId());
                for(AndOrOrStrategyVO andOrOrStrategyVO:andOrOrStrategyVOS){
                   CourseLimitVO courseLimitVO=strategyDao.getCourseLimitByStrategyId(andOrOrStrategyVO.getStrategyId());
                   int studentNumber=0;
                   for(BigInteger studentId:studentIdList){
                       studentNumber+=strategyDao.getCourseStudentNumber(courseLimitVO.getCourseId(),studentId);
                   }
                   if(studentNumber>courseLimitVO.getMaxMember()||studentNumber<courseLimitVO.getMinMember()) return false;
                }
            }else if(item.getStrategyName().equals("TeamOrStrategy")){
                System.out.println("TeamOrStrategy");
                boolean flag=false;
                List<AndOrOrStrategyVO> andOrOrStrategyVOS=strategyDao.selectOrStrategy(item.getStrategyId());
                for(AndOrOrStrategyVO andOrOrStrategyVO:andOrOrStrategyVOS){
                    CourseLimitVO courseLimitVO=strategyDao.getCourseLimitByStrategyId(andOrOrStrategyVO.getStrategyId());
                    int studentNumber=0;
                    for(BigInteger studentId:studentIdList){
                        studentNumber+=strategyDao.getCourseStudentNumber(courseLimitVO.getCourseId(),studentId);
                    }
                    if(studentNumber<courseLimitVO.getMaxMember()&&studentNumber>courseLimitVO.getMinMember()) {flag=true;break;}
                }
                if(flag=true) continue;
                else return false;
            }
            else if(item.getStrategyName().equals("ConflictCourseStrategy")){
                System.out.println("ConflictCourseStrategy");
                List<BigInteger> conflictCourseIdList=strategyDao.listConflictCourseId(item.getStrategyId());
                for(BigInteger courseIdItem:conflictCourseIdList){
                    for(BigInteger studentId:studentIdList){
                        if(strategyDao.getCourseStudentNumber(courseIdItem,studentId)>0)return false;
                    }
                }
            }
        }
        return true;
   }
}
