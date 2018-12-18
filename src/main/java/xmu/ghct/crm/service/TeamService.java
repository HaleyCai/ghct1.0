package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.TeamDao;
import xmu.ghct.crm.entity.Team;
import xmu.ghct.crm.entity.User;

import java.math.BigInteger;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    TeamDao teamDao;

    public List<Team> getTeamInfoByCourseId(BigInteger courseId) {
        return teamDao.getTeamInfoByCourseId(courseId);
    }

    public List<User> getNoTeamStudentByCourseId(BigInteger courseId) {
        return teamDao.getNoTeamStudentByCourseId(courseId);
    }
}
