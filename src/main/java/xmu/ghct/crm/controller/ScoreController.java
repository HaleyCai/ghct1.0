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
}
