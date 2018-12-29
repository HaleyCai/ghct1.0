package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.ScoreVO;
import xmu.ghct.crm.VO.SeminarVO;
import xmu.ghct.crm.entity.Score;
import xmu.ghct.crm.entity.Seminar;
import xmu.ghct.crm.service.ScoreService;
import xmu.ghct.crm.service.SeminarService;
import xmu.ghct.crm.service.TeamService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ScoreController {

    @Autowired
    ScoreService scoreService;

    @Autowired
    SeminarService seminarService;

    @Autowired
    TeamService teamService;

    //////////////////////需要重写
    @RequestMapping(value="/course/{courseId}/score",method = RequestMethod.GET)
    public List<ScoreVO> listScoreByCourseId(@PathVariable("courseId") BigInteger courseId){
        return scoreService.listAllScoreByCourseId(courseId);
    }

    /**
     * @cyq
     * 自己写的api！！！！
     * 根据某小组查看自己的某次讨论课的成绩：每次讨论课成绩+总成绩
     * @param courseId
     * @param teamId
     * @return
     */
    //////////////////////需要重写
    @RequestMapping(value="/course/{courseId}/team/{teamId}/score",method = RequestMethod.GET)
    public List<ScoreVO> getScoreByRoundIdTeamId(@PathVariable("courseId") BigInteger courseId,
                                       @PathVariable("teamId") BigInteger teamId)
    {
        return scoreService.listTeamScoreByCourseId(courseId,teamId);
    }


    /**
     * @author hzm
     *根据roundId获得轮次下所有小组的轮次成绩
     * @param roundId
     * @return
     */
    @GetMapping("/course/round/{roundId}")
    public List<ScoreVO> listRoundScoreByRoundId(@PathVariable("roundId") BigInteger roundId){
        return scoreService.listRoundScoreByRoundId(roundId);
    }

    @GetMapping("/course/round/{roundId}/{teamId}")
    public List<Score> listKlassSeminarScoreByRoundIdAndTeamId(@PathVariable("roundId")BigInteger roundId,@PathVariable("teamId")BigInteger teamId){
        List<BigInteger> seminarIdList=seminarService.listSeminarIdByRoundId(roundId);
        BigInteger klassId=teamService.getKlassIdByTeamId(teamId);
        List<SeminarVO> klassSeminarList=new ArrayList<>();
        List<Score> scoreList=new ArrayList<>();
        for(BigInteger item:seminarIdList){
            SeminarVO seminarVO=seminarService.getKlassSeminarByKlassIdAndSeminarId(klassId,item);
            klassSeminarList.add(seminarVO);
        }
        for(SeminarVO item:klassSeminarList){
            Score score=scoreService.getKlassSeminarScoreByKlassSeminarIdAndTeamId(item.getKlassSeminarId(),teamId);
            Seminar seminar=seminarService.getSeminarBySeminarId(item.getSeminarId());
            if(score!=null){
                score.setSeminarName(seminar.getSeminarName());
                scoreList.add(score);
            }
        }
        return scoreList;
    }





}
