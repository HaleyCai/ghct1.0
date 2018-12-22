package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.StudentVO;
import xmu.ghct.crm.VO.TeamInfoVO;
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

    public List<TeamInfoVO> listTeamInfoByCourseId(BigInteger courseId) {
        List<Team> teamList=teamDao.listTeamInfoByCourseId(courseId);
        List<TeamInfoVO> teamInfoVOList=new ArrayList<>();
        for(Team teamItem:teamList){
            TeamInfoVO teamInfoVO=new TeamInfoVO();
            teamInfoVO.setTeamSerial(teamItem.getTeamSerial());
            teamInfoVO.setTeamName(teamItem.getTeamName());
            teamInfoVO.setTeamId(teamItem.getTeamId());
            teamInfoVO.setStatus(teamItem.getStatus());
            StudentVO studentVO=new StudentVO();
            User leader=studentDao.getStudentByStudentId(teamItem.getLeaderId());
            studentVO.setAccount(leader.getAccount());
            studentVO.setEmail(leader.getEmail());
            studentVO.setName(leader.getName());
            studentVO.setStudentId(leader.getId());
            studentVO.setTeamId(leader.getTeamId());
            teamInfoVO.setTeamLeader(studentVO);
            List<BigInteger> studentIdList=teamDao.getStudentIdByTeamId(teamItem.getTeamId());
            List<StudentVO> members=new ArrayList<>();
            for(BigInteger studentIdItem:studentIdList){
                if(studentIdItem==teamItem.getLeaderId()) continue;
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
            teamInfoVOList.add(teamInfoVO);
        }
        return teamInfoVOList;
    }

    public List<User> getNoTeamStudentByCourseId(BigInteger courseId) {
        return teamDao.getNoTeamStudentByCourseId(courseId);
    }
}
