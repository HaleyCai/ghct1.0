package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.ScoreVO;
import xmu.ghct.crm.VO.SeminarSimpleVO;
import xmu.ghct.crm.VO.SeminarVO;
import xmu.ghct.crm.dao.RoundDao;
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
    /*
    @RequestMapping(value="/course/{courseId}/score",method = RequestMethod.GET)
    public List<ScoreVO> listScoreByCourseId(@PathVariable("courseId") BigInteger courseId){
        return scoreService.listAllScoreByCourseId(courseId);
    }
    */

    /**
     * @cyq
     * 自己写的api！！！！
     * 根据某小组查看自己的某次讨论课的成绩：每次讨论课成绩+总成绩
     * @param courseId
     * @param teamId
     * @return
     */
    //////////////////////需要重写
    /*
    @RequestMapping(value="/course/{courseId}/team/{teamId}/score",method = RequestMethod.GET)
    public List<ScoreVO> getScoreByRoundIdTeamId(@PathVariable("courseId") BigInteger courseId,
                                       @PathVariable("teamId") BigInteger teamId)
    {
        return scoreService.listTeamScoreByCourseId(courseId,teamId);
    }
    */


    /**
     * @author hzm
     *根据roundId获得轮次下所有小组的轮次成绩
     * @param roundId
     * @return
     */
    @GetMapping("/course/round/{roundId}")
    public List<ScoreVO> listRoundScoreByRoundId(@PathVariable("roundId") Long roundId){
        return scoreService.listRoundScoreByRoundId(BigInteger.valueOf(roundId));
    }


    /**
     * 获取小组某轮次下所有讨论课的成绩信息
     * @param roundId
     * @param teamId
     * @return
     */
    @GetMapping("/course/round/{roundId}/{teamId}")
    public List<Score> listKlassSeminarScoreByRoundIdAndTeamId(@PathVariable("roundId")Long roundId,
                                                               @PathVariable("teamId")Long teamId){
        List<BigInteger> seminarIdList=seminarService.listSeminarIdByRoundId(BigInteger.valueOf(roundId));
        BigInteger klassId=teamService.getKlassIdByTeamId(BigInteger.valueOf(teamId));
        List<SeminarVO> klassSeminarList=new ArrayList<>();
        List<Score> scoreList=new ArrayList<>();
        for(BigInteger item:seminarIdList){
            SeminarVO seminarVO=seminarService.getKlassSeminarByKlassIdAndSeminarId(klassId,item);
            klassSeminarList.add(seminarVO);
        }
        for(SeminarVO item:klassSeminarList){
            Score score=scoreService.getKlassSeminarScoreByKlassSeminarIdAndTeamId(item.getKlassSeminarId(),BigInteger.valueOf(teamId));
            Seminar seminar=seminarService.getSeminarBySeminarId(item.getSeminarId());
            if(score!=null){
                score.setSeminarName(seminar.getSeminarName());
                scoreList.add(score);
            }
        }
        return scoreList;
    }


    /**
     * 获得某小组所有轮次的简单信息（roundId，轮次序号，轮次总成绩）
     * @param courseId
     * @param teamId
     * @return
     */
    @GetMapping("/course/{courseId}/{teamId}")
    public List<Map<String,Object>> listTeamRoundInfoByCourseIdAndTeamId(@PathVariable("courseId")Long courseId,
                                                                         @PathVariable("teamId")Long teamId){
        return scoreService.listTeamRoundInfoByCourseIdAndTeamId(BigInteger.valueOf(courseId),BigInteger.valueOf(teamId));
    }

    /**
     * 获取某小组某轮次所有讨论课简单信息
     * @param roundId
     * @return
     */
    @GetMapping("/course/round/{roundId}/{teamId}/roundSeminar")
    public List<SeminarSimpleVO> getSeminarByRoundId(@PathVariable("roundId") Long roundId,
                                                     @PathVariable("teamId") Long teamId){
        return scoreService.getSeminarByRoundId(BigInteger.valueOf(roundId),BigInteger.valueOf(teamId));
    }


    /**
     *获取某小组某次讨论课成绩
     * @param seminarId
     * @param teamId
     * @return
     */
    @GetMapping("/course/round/{teamId}/{seminarId}/seminarScore")
    public Score getTeamSeminarScoreBySeminarIdAndTeamId(@PathVariable("seminarId")Long seminarId,
                                                         @PathVariable("teamId")Long teamId){
        return scoreService.getTeamSeminarScoreBySeminarIdAndTeamId(BigInteger.valueOf(seminarId),BigInteger.valueOf(teamId));

    }





}
