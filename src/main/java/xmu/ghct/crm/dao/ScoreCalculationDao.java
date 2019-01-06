package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.vo.ScoreVO;
import xmu.ghct.crm.entity.Round;
import xmu.ghct.crm.entity.Score;
import xmu.ghct.crm.mapper.RoundMapper;
import xmu.ghct.crm.mapper.ScoreMapper;
import xmu.ghct.crm.mapper.SeminarMapper;
import xmu.ghct.crm.mapper.TeamMapper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hzm
 */
@Component
public class ScoreCalculationDao {

    @Autowired
    RoundMapper roundMapper;

    @Autowired
    SeminarMapper seminarMapper;

    @Autowired
    ScoreMapper scoreMapper;

    @Autowired
    TotalScoreDao totalScoreDao;

    @Autowired
    TeamMapper teamMapper;

    public Score roundScoreCalculation(Score score, BigInteger roundId,BigInteger teamId,BigInteger courseId)
    {
        Score roundScore=new Score();
        BigInteger klassId=teamMapper.getKlassIdByTeamId(teamId);
        Round round=roundMapper.getRoundByRoundId(roundId);
        ScoreVO roundScoreVO=scoreMapper.getTeamRoundScore(roundId,teamId);
        List<BigInteger> seminarIdList=seminarMapper.getSeminarIdByRoundId(roundId);
        List<BigInteger> klassSeminarIdList=new ArrayList<>();
        for(BigInteger item:seminarIdList){
            BigInteger klassSeminarId=seminarMapper.getKlassSeminarIdBySeminarIdAndKlassId(item,klassId);
            if(klassSeminarId!=null) {
                klassSeminarIdList.add(klassSeminarId);
            }
        }
        List<Score> seminarScoreList=new ArrayList<>();
        int a=0;
        for(BigInteger item:klassSeminarIdList){
            System.out.println("seminarId"+item);
            System.out.println("teamId"+teamId);
            Score score1=scoreMapper.getSeminarScoreByKlassSeminarIdAndTeamId(item,teamId);
            if(score1 != null){
            seminarScoreList.add(score1);}}
        if(round.getPresentationScoreMethod()==0){
            double presentation=0;
            int account=0;
            for(Score item:seminarScoreList) {
                presentation += item.getPresentationScore();
                account++;
            }
            presentation/=account;
            roundScore.setPresentationScore(presentation);
        }
        else {
            if(roundScoreVO.getPresentationScore()>score.getPresentationScore()) {
                roundScore.setPresentationScore(roundScoreVO.getPresentationScore());
            } else {
                roundScore.setPresentationScore(score.getPresentationScore());
            }
        }
        if(round.getQuestionScoreMethod()==0){
            double question=0;
            int account=0;
            for(Score item:seminarScoreList) {
                question += item.getQuestionScore();
                account++;
            }
            System.out.println("question account:"+account);
            question/=account;
            roundScore.setQuestionScore(question);
        }
        else {
            if(roundScoreVO.getQuestionScore()>score.getQuestionScore()) {
                roundScore.setQuestionScore(roundScoreVO.getQuestionScore());
            } else {
                roundScore.setQuestionScore(score.getQuestionScore());
            }
        }
        if(round.getReportScoreMethod()==0){
            double report=0;
            int account=0;
            for(Score item:seminarScoreList) {
                report += item.getReportScore();
                account++;
            }
            report/=account;
            roundScore.setReportScore(report);
        }
        else {
            if(roundScoreVO.getReportScore()>score.getReportScore()) {
                roundScore.setReportScore(roundScoreVO.getReportScore());
            } else {
                roundScore.setReportScore(score.getReportScore());
            }
        }
        roundScore=totalScoreDao.totalScoreCalculation(roundScore,courseId);
        return roundScore;
    }
}
