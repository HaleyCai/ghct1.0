package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.vo.ScoreVO;
import xmu.ghct.crm.entity.Score;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.mapper.RoundMapper;
import xmu.ghct.crm.mapper.ScoreMapper;
import xmu.ghct.crm.mapper.SeminarMapper;
import xmu.ghct.crm.mapper.TeamMapper;

import java.math.BigInteger;
import java.util.*;

/**
 * @author hzm,caiyq
 */
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


    /**
     * 根据课程查找所有的成绩
     * @return
    public List<ScoreVO> listScoreByCourseId(BigInteger courseId){
    List<Round> roundList=roundMapper.listRoundByCourseId(courseId);  //获得该课程下的所有轮次
    List<ScoreVO> allScoreVOList=new ArrayList<>();                   //存储所有的小组的成绩ScoreVO
    for(Round roundItem:roundList){                                   //依次循环每一轮次
    List<ScoreVO> scoreVOList=scoreMapper.getRoundScore(roundItem.getRoundId());                  //在round_score表查询该轮次下的总成绩
    List<BigInteger> seminarIdList=seminarMapper.getSeminarIdByRoundId(roundItem.getRoundId());   //获得该课程该轮次下的所有讨论课ID
    for(ScoreVO scoreVOItem:scoreVOList){
    //一个scoreVO，对应一个team的一个轮次成绩，查找一个team在该轮次的所有klass_seminar的每次成绩，加入scoreVO的list
    //写入round序号
    scoreVOItem.setRoundSerial(roundItem.getRoundSerial());
    //写入team序号
    scoreVOItem.setTeamSerial(teamMapper.getTeamInfoByTeamId(scoreVOItem.getTeamId()).getTeamSerial());
    List<SeminarScoreVO> scoreList= new ArrayList<>();
    for(BigInteger sItem:seminarIdList) {
    //查出每一个seminar下的，该小组参加的，klass_seminar
    //所有的klass_seminar
    List<BigInteger> klassSeminarIdList=seminarMapper.getAllKlassSeminarIdBySeminarId(sItem);
    //依次遍历每节讨论课，查找该team参加的讨论课成绩
    for(BigInteger ksItem:klassSeminarIdList){
    Score klassSeminarScore=scoreMapper.getSeminarScoreByKlassSeminarIdAndTeamId(ksItem, scoreVOItem.getTeamId());
    if(klassSeminarScore!=null)
    {
    //Score转SeminarScoreVO
    scoreList.add(scoreTOSeminarScoreVO(sItem,ksItem,klassSeminarScore));
    }
    }
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
     */

/*
    public List<ScoreVO> listTeamScoreByCourseId(BigInteger courseId,BigInteger teamId)
    {
        List<Round> roundList=roundMapper.listRoundByCourseId(courseId);  //获得该课程下的所有轮次
        List<ScoreVO> allScoreVOList=new ArrayList<>();                   //存储所有的小组的成绩ScoreVO
        for(Round roundItem:roundList)
        {
            ScoreVO scoreVO=scoreMapper.getTeamRoundScore(roundItem.getRoundId(),teamId);
            scoreVO.setRoundSerial(roundItem.getRoundSerial());
            scoreVO.setTeamSerial(teamMapper.getTeamInfoByTeamId(teamId).getTeamSerial());
            //查找该轮次下所有的讨论课
            List<BigInteger> seminarIdList=seminarMapper.getSeminarIdByRoundId(roundItem.getRoundId());
            List<SeminarScoreVO> scoreList= new ArrayList<>();
            //查找所有的讨论课下，本小组参加的讨论课的成绩
            for(BigInteger sItem:seminarIdList)
            {
                List<BigInteger> klassSeminarIdList=seminarMapper.getAllKlassSeminarIdBySeminarId(sItem);
                //依次遍历每节讨论课，查找该team参加的讨论课成绩
                for(BigInteger ksItem:klassSeminarIdList) {
                    Score klassSeminarScore = scoreMapper.getSeminarScoreByKlassSeminarIdAndTeamId(ksItem, teamId);
                    if (klassSeminarScore != null) {
                        scoreList.add(scoreTOSeminarScoreVO(sItem,ksItem,klassSeminarScore));
                    }
                }
            }
            scoreVO.setScoreList(scoreList);         //添加讨论课List进ScoreVO
            allScoreVOList.add(scoreVO);         //将ScoreVO加进 allScoreVOList
        }
        if(allScoreVOList==null){
            //throw new TeamNotFindException();
        }
        return allScoreVOList;
    }
    */

    /*
    public SeminarScoreVO scoreTOSeminarScoreVO(BigInteger sItem,BigInteger ksItem,Score score)
    {
        //Score转SeminarScoreVO
        SeminarScoreVO klassSeminarScoreVO = new SeminarScoreVO();
        klassSeminarScoreVO.setSeminarId(sItem);
        klassSeminarScoreVO.setKlassSeminarId(ksItem);
        //查seminar name返回
        klassSeminarScoreVO.setSeminarName(seminarMapper.getSeminarNameBySeminarId(sItem));
        //查seminar serial返回，前端在显示时排序
        klassSeminarScoreVO.setSeminarSerial(seminarMapper.getSeminarSerialBySeminarId(sItem));
        klassSeminarScoreVO.setPresentationScore(score.getPresentationScore());
        klassSeminarScoreVO.setReportScore(score.getReportScore());
        klassSeminarScoreVO.setQuestionScore(score.getQuestionScore());
        klassSeminarScoreVO.setTotalScore(score.getTotalScore());
        return klassSeminarScoreVO;
    }
*/

    public int deleteSeminarScoreBySeminarId(BigInteger seminarId) throws NotFoundException {
        int count=scoreMapper.deleteSeminarScoreBySeminarId(seminarId);
        if(count<=0)
        {
            throw new NotFoundException("该讨论课成绩不存在");
        }
        return count;
    }

    public  int updateSeminarScoreBySeminarIdAndTeamId(Score score) throws NotFoundException{
        int count=scoreMapper.updateSeminarScoreBySeminarIdAndTeamId(score);
        if(count<=0)
        {
            throw new NotFoundException("该讨论课成绩不存在");
        }
        return count;
    }


    public int updateRoundScoreByRoundIdAndTeamId(ScoreVO scoreVO) throws NotFoundException
    {
        int count=scoreMapper.updateRoundScoreByRoundIdAndTeamId(scoreVO);
        System.out.println(scoreVO);
        if(count<=0)
        {
            throw new NotFoundException("该轮成绩不存在");
        }
        return count;
    }

    public Score getSeminarScoreByKlassSeminarIdAndTeamId(BigInteger klassSeminarId,BigInteger teamId) throws NotFoundException
    {
        Score score=scoreMapper.getSeminarScoreByKlassSeminarIdAndTeamId(klassSeminarId,teamId);
        if(score==null)
        {
            score=new Score();
            score.setKlassSeminarId(klassSeminarId);
            score.setTeamId(teamId);
            score.setPresentationScore(0);
            score.setQuestionScore(0);
            score.setReportScore(0);
            score.setTotalScore(0);
        }
        return score;
    }

    public ScoreVO getTeamRoundScoreByRoundIdAndTeamId(BigInteger roundId,BigInteger teamId)
    {
        ScoreVO score=scoreMapper.getTeamRoundScore(roundId,teamId);
        if(score==null)
        {
            score=new ScoreVO();
            score.setTeamId(teamId);
            score.setRoundId(roundId);
            score.setPresentationScore(0);
            score.setReportScore(0);
            score.setQuestionScore(0);
            score.setTotalScore(0);
        }
        return score;
    }


    /**
     * @author hzm
     *根据roundId获得轮次下所有小组的轮次成绩
     * @param roundId
     * @return
     */
    public List<ScoreVO> listRoundScoreByRoundId(BigInteger roundId) throws NotFoundException{
        List<ScoreVO> scoreVO=scoreMapper.listRoundScoreByRoundId(roundId);
        if(scoreVO==null&&scoreVO.isEmpty())
        {
            throw new NotFoundException("轮次成绩列表不存在");
        }
        return scoreVO;
    }

    /**
     * @author hzm
     * 创建轮次成绩
     * @param scoreVO
     * @return
     */
    //public int insertRoundScore(ScoreVO scoreVO){
    //       return scoreMapper.insertRoundScore(scoreVO);
    //  }
}