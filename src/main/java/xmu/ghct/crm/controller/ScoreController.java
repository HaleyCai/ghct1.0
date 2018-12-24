package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.ScoreVO;
import xmu.ghct.crm.entity.Score;
import xmu.ghct.crm.service.ScoreService;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ScoreController {

    @Autowired
    ScoreService scoreService;

    @RequestMapping(value="/course/{courseId}/score",method = RequestMethod.GET)
    public List<ScoreVO> listScoreByCourseId(@PathVariable("courseId") BigInteger courseId){
        return scoreService.listAllScoreByCourseId(courseId);
    }

    /**
     * @cyq
     * 根据roundId查找某小组某次讨论课的成绩：每次讨论课成绩+总成绩
     * @param roundId
     * @param teamId
     * @return
     */
    @RequestMapping(value="/round/{roundId}/team/{teamId}/roundscore")
    public Map getScoreByRoundIdTeamId(@PathVariable("roundId") BigInteger roundId,
                                       @PathVariable("teamId") BigInteger teamId)
    {
        Map<String,Object> result=new HashMap<>();
        //先找到该轮次下的所有讨论课，再查找每次讨论课成绩，再找轮次成绩
        ScoreVO roundScore=scoreService.getTeamRoundScore(roundId,teamId);
        result.put("roundScore",roundScore);
        return result;
    }
}
