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
            if(klassSeminarId!=null) klassSeminarIdList.add(klassSeminarId);
        }
        List<Score> seminarScoreList=new ArrayList<>();
        int a=0;
        for(BigInteger item:klassSeminarIdList){
            System.out.println("seminarId"+item);
            System.out.println("teamId"+teamId);
            Score score_1=scoreMapper.getSeminarScoreByKlassSeminarIdAndTeamId(item,teamId);
            if(score_1 != null){
            seminarScoreList.add(score_1);}}
        if(round.getPresentationScoreMethod()==0){
            double presentation=0;
            int account=0;
            for(Score item:seminarScoreList) {
                presentation += item.getPresentationScore();
                account++;
        //        System.out.println(account+"个presentation成绩为： "+item.getPresentationScore());
            }
            System.out.println("presentation account:"+account);
        //    System.out.println("presentation总成绩为： "+presentation);
            presentation/=account;
       //     System.out.println("presentation成绩为： "+presentation);
            roundScore.setPresentationScore(presentation);
        }
        else {
            if(roundScoreVO.getPresentationScore()>score.getPresentationScore()) roundScore.setPresentationScore(roundScoreVO.getPresentationScore());
            else roundScore.setPresentationScore(score.getPresentationScore());
     //       System.out.println("presentation成绩为： "+roundScore.getPresentationScore());
        }
        if(round.getQuestionScoreMethod()==0){
            double question=0;
            int account=0;
            for(Score item:seminarScoreList) {
                question += item.getQuestionScore();
                account++;
            //    System.out.println(account+"个question成绩为： "+item.getQuestionScore());
            }
            System.out.println("question account:"+account);
        //    System.out.println("question总成绩为： "+question);
            question/=account;
        //    System.out.println("question成绩为： "+question);
            roundScore.setQuestionScore(question);
        }
        else {
            if(roundScoreVO.getQuestionScore()>score.getQuestionScore()) roundScore.setQuestionScore(roundScoreVO.getQuestionScore());
            else roundScore.setQuestionScore(score.getQuestionScore());
          //  System.out.println("question成绩为： "+roundScore.getQuestionScore());
        }
        if(round.getReportScoreMethod()==0){
            double report=0;
            int account=0;
            for(Score item:seminarScoreList) {
                report += item.getReportScore();
                account++;
           //     System.out.println(account+"个report成绩为： "+item.getReportScore());
            }
          //  System.out.println("report总成绩为： "+report);
            System.out.println("report account:"+account);
            report/=account;
         //   System.out.println("report成绩为： "+report);
            roundScore.setReportScore(report);
        }
        else {
            if(roundScoreVO.getReportScore()>score.getReportScore()) roundScore.setReportScore(roundScoreVO.getReportScore());
            else roundScore.setReportScore(score.getReportScore());
          //  System.out.println("report成绩为： "+roundScore.getReportScore());
        }
        roundScore=totalScoreDao.totalScoreCalculation(roundScore,courseId);
       // System.out.println("total成绩为： "+roundScore.getTotalScore());
        return roundScore;
    }
}
