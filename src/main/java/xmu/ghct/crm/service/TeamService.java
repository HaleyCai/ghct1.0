package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.StudentVO;
import xmu.ghct.crm.VO.TeamInfoVO;
import xmu.ghct.crm.VO.TeamSimpleInfo;
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

    /**
     * @cyq
     * 教师，查课程下所有队伍的简单信息
     * @param courseId
     * @return
     */
    public List<TeamSimpleInfo> listTeamByCourseId(BigInteger courseId) {
        List<Team> teamList=teamDao.listTeamInfoByCourseId(courseId);
        return teamTOTeamSimpleInfo(teamList);
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
            teamSimpleInfo.setTeamSerial(teamItem.getTeamSerial());
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

    public List<StudentVO> getNoTeamStudentByKlassId(BigInteger klassId) {
        return teamDao.getNoTeamStudentByKlassId(klassId);
    }
}
