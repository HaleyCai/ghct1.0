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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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

    public Score roundScoreCalculation(Score score, BigInteger roundId,BigInteger teamId,BigInteger courseId)
    {
        Score roundScore=new Score();
        Round round=roundMapper.getRoundByRoundId(roundId);
        ScoreVO roundScoreVO=scoreMapper.getTeamRoundScore(roundId,teamId);
        List<BigInteger> seminarIdList=seminarMapper.getSeminarIdByRoundId(roundId);
        List<Score> seminarScoreList=new ArrayList<>();
        for(BigInteger item:seminarIdList)
            seminarScoreList.add(scoreMapper.getSeminarScoreBySeminarIdAndTeamId(item,teamId));
        if(round.getPresentationScoreMethod()==0){
            double presentation=0;
            int account=1;
            for(Score item:seminarScoreList) {
                presentation += presentation +item.getPresentationScore();
                account++;
            }
            presentation+=score.getPresentationScore();
            presentation/=account;
            roundScore.setPresentationScore(presentation);
        }
        else {
            if(roundScoreVO.getPresentationScore()>score.getPresentationScore()) roundScore.setPresentationScore(roundScoreVO.getPresentationScore());
            else roundScore.setPresentationScore(score.getPresentationScore());
        }
        if(round.getQuestionScoreMethod()==0){
            double question=0;
            int account=1;
            for(Score item:seminarScoreList) {
                question += question +item.getQuestionScore();
                account++;
            }
            question+=score.getQuestionScore();
            question/=account;
            roundScore.setQuestionScore(question);
        }
        else {
            if(roundScoreVO.getQuestionScore()>score.getQuestionScore()) roundScore.setQuestionScore(roundScoreVO.getQuestionScore());
            else roundScore.setQuestionScore(score.getQuestionScore());
        }
        if(round.getReportScoreMethod()==0){
            double report=0;
            int account=1;
            for(Score item:seminarScoreList) {
                report += report +item.getReportScore();
                account++;
            }
            report+=score.getReportScore();
            report/=account;
            roundScore.setReportScore(report);
        }
        else {
            if(roundScoreVO.getReportScore()>score.getReportScore()) roundScore.setReportScore(roundScoreVO.getReportScore());
            else roundScore.setReportScore(score.getReportScore());
        }
        roundScore=totalScoreDao.totalScoreCalculation(roundScore,courseId);
        return roundScore;
    }
}
