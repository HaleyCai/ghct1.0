package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Score;
import xmu.ghct.crm.mapper.ScoreMapper;
import xmu.ghct.crm.mapper.TeamMapper;

import java.math.BigInteger;
import java.util.List;

@Component
public class ScoreDao {
    @Autowired
    TeamMapper teamMapper;

    @Autowired
    ScoreMapper scoreMapper;

    public List<Score> listScoreByCourseId(BigInteger courseId){
        BigInteger teamId=teamMapper.getTeamIdByCourseId(courseId);
        List<Score> scoreList=scoreMapper.listScoreByTeamId(teamId);
        if(scoreList==null){
            //throw new TeamNotFindException();
        }
        return scoreList;
    }
}
