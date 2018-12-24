package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Team;
import xmu.ghct.crm.entity.User;
import xmu.ghct.crm.mapper.CourseMapper;
import xmu.ghct.crm.mapper.TeamMapper;

import java.math.BigInteger;
import java.util.List;

@Component
public class TeamDao {
    @Autowired
    TeamMapper teamMapper;

    public List<Team> listTeamInfoByCourseId(BigInteger courseId) {
        List<Team> teamList = teamMapper.listTeamInfoByCourseId(courseId);
        return teamList;
    }

    public List<User> getNoTeamStudentByCourseId(BigInteger courseId) {
        List<User> studentList = teamMapper.getNoTeamStudentByCourseId(courseId);
        if (studentList == null) {
            //throw new NoStudentNotFindException();
        }
        return studentList;
    }

    public  List<BigInteger> getStudentIdByTeamId(BigInteger teamId){
        return teamMapper.getStudentIdByTeamId(teamId);
    }

    public Team getTeamInfoByTeamId(BigInteger teamId){
        return teamMapper.getTeamInfoByTeamId(teamId);
    }

    public List<Team> listTeamByKlassId(BigInteger klassId) {
        return  teamMapper.listTeamByKlassId(klassId);
    }

}
