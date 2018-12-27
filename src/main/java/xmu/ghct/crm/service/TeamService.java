package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.*;
import xmu.ghct.crm.dao.KlassDao;
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
    /**
     * @cyq
     * 教师，查课程下所有队伍的简单信息
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
     * 学生，查课程的本班级下所有队伍的简单信息
     * @param klassId
     * @return
     */
    public List<TeamSimpleInfo> listTeamByKlassId(BigInteger klassId){
        List<Team> teamList=teamDao.listTeamByKlassId(klassId);
        return teamTOTeamSimpleInfo(teamList);
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
     * 获取班级里未组队学生
     * @param klassId
     * @return
     */
    public List<StudentVO> getNoTeamStudentByKlassId(BigInteger klassId) {
        return teamDao.getNoTeamStudentByKlassId(klassId);
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
}
