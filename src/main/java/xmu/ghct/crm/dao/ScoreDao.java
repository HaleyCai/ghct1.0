package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Score;
import xmu.ghct.crm.mapper.ScoreMapper;
import xmu.ghct.crm.mapper.TeamMapper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class ScoreDao {
    @Autowired
    TeamMapper teamMapper;

    @Autowired
    ScoreMapper scoreMapper;

    public List<Score> listScoreByCourseId(BigInteger courseId){
        List<BigInteger> teamIdList=teamMapper.getTeamIdByCourseId(courseId);
        List<Score> scoreList=new ArrayList<>();
        for(BigInteger item:teamIdList)
            for(Score scoreItem:scoreMapper.listScoreByTeamId(item))
                scoreList.add(scoreItem);
        if(scoreList==null){
            //throw new TeamNotFindException();
        }
        return scoreList;
    }
}
