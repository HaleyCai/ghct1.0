package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.ScoreVO;
import xmu.ghct.crm.entity.Round;
import xmu.ghct.crm.entity.Score;
import xmu.ghct.crm.mapper.RoundMapper;
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

    @Autowired
    RoundMapper roundMapper;


    public List<ScoreVO> listScoreByCourseId(BigInteger courseId){
        List<BigInteger> teamIdList=teamMapper.getTeamIdByCourseId(courseId);
        List<Round> roundList=roundMapper.listRoundByCourseId(courseId);
        List<Score> scoreVOList=new ArrayList<>();
        List<Score> scoreList=new ArrayList<>();
        for(Round roundItem:roundList){

        }
        for(BigInteger item:teamIdList)
            for(Score scoreItem:scoreMapper.listScoreByTeamId(item))
            {

            }
        if(scoreList==null){
            //throw new TeamNotFindException();
        }
        return scoreList;
    }

}
