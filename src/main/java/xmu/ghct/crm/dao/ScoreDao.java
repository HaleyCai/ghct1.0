package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.ScoreVO;
import xmu.ghct.crm.entity.Round;
import xmu.ghct.crm.entity.Score;
import xmu.ghct.crm.entity.Seminar;
import xmu.ghct.crm.mapper.RoundMapper;
import xmu.ghct.crm.mapper.ScoreMapper;
import xmu.ghct.crm.mapper.SeminarMapper;
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

    @Autowired
    SeminarMapper seminarMapper;


    public List<ScoreVO> listScoreByCourseId(BigInteger courseId){
   //     List<BigInteger> teamIdList=teamMapper.getTeamIdByCourseId(courseId);
        List<Round> roundList=roundMapper.listRoundByCourseId(courseId);  //获得该课程下的所有轮次
        List<ScoreVO> allScoreVOList=new ArrayList<>();                   //存储所有的小组的成绩ScoreVO
        for(Round roundItem:roundList){                                   //依次循环每一轮次
            List<ScoreVO> scoreVOList=scoreMapper.getRoundScore(roundItem.getRoundId());                  //在round_score表查询该轮次下的所有成绩
            List<BigInteger> seminarIdList=seminarMapper.getSeminarIdByRoundId(roundItem.getRoundId());   //获得该轮次下的所有讨论课ID
            for(ScoreVO scoreVOItem:scoreVOList){                                                         //依次循环每一项round_score项，即每一个组的轮次成绩信息
                List<Score> scoreList= new ArrayList<>();                                                 //新建用以存储该小组该次讨论课下的所有讨论课成绩
                for(BigInteger bItem:seminarIdList) {                                                     //依次遍历该轮次所有讨论课，与teamID与操作查找该小组的讨论课成绩
                    Score scoreItem=scoreMapper.getScoreBySeminarIdAndTeamId(bItem, scoreVOItem.getTeamId());
                    if(scoreItem!=null)
                        scoreList.add(scoreItem);
                }
                scoreVOItem.setScoreList(scoreList);     //添加讨论课List进ScoreVO
                allScoreVOList.add(scoreVOItem);         //将ScoreVO加进 allScoreVOList
            }
        }
        if(allScoreVOList==null){
            //throw new TeamNotFindException();
        }
        return allScoreVOList;
    }

    //1.根据courseId查所有的roundId【表round】
    //2.1.根据roundId查所有的轮次成绩【表round_score】（总成绩）+每节seminar的成绩=》
    //2.2.根据roundId查其下所有的seminarId【表seminar】
    //2.3.根据seminarId查所有的klassSeminarId【表klass_seminar】
    //2.4.根据klassSeminarId查所有的team的每节seminar成绩【表seminar_score】






    public int deleteSeminarScoreBySeminarId(BigInteger seminarId){
        return scoreMapper.deleteSeminarScoreBySeminarId(seminarId);
    }

    /**
     * @cyq
     * 根据roundId获取轮次下所有成绩
     * @param roundId
     * @return
     */
    public List<ScoreVO> listScoreByRoundId(BigInteger roundId){
        return scoreMapper.getRoundScore(roundId);
    }

    /**
     * @cyq
     * 某小组，某轮次的总成绩
     * @param roundId
     * @param teamId
     * @return
     */
    public ScoreVO getTeamRoundScore(BigInteger roundId,BigInteger teamId)
    {
        return scoreMapper.getRoundScoreByRoundIdAndTeamId(roundId,teamId);
    }

    public  int updateSeminarScoreBySeminarIdAndTeamId(Score score){
        return scoreMapper.updateSeminarScoreBySeminarIdAndTeamId(score);
    }


    public int updateRoundScoreByRoundIdAndTeamId(ScoreVO scoreVO){
        return scoreMapper.updateRoundScoreByRoundIdAndTeamId(scoreVO);
    }
}
