package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.entity.Score;
import xmu.ghct.crm.service.ScoreService;

import java.math.BigInteger;
import java.util.List;

@RestController
public class ScoreController {

    @Autowired
    ScoreService scoreService;

    @RequestMapping(value="/course/{courseId}/score",method = RequestMethod.GET)
    public List<Score> listScoreByCourseId(@PathVariable("courseId") BigInteger courseId){
        return scoreService.listScoreByCourseId(courseId);
    }
}
