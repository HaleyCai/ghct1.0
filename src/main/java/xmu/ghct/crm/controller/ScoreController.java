package xmu.ghct.crm.controller;

import xmu.ghct.crm.entity.Round;
import xmu.ghct.crm.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.ScoreVO;
import xmu.ghct.crm.VO.SeminarSimpleVO;
import xmu.ghct.crm.VO.SeminarVO;
import xmu.ghct.crm.dao.RoundDao;
import xmu.ghct.crm.entity.Score;
import xmu.ghct.crm.entity.Seminar;
import xmu.ghct.crm.security.JwtTokenUtil;
import xmu.ghct.crm.service.CourseService;
import xmu.ghct.crm.service.ScoreService;
import xmu.ghct.crm.service.SeminarService;
import xmu.ghct.crm.service.TeamService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
public class ScoreController {

    @Autowired
    ScoreService scoreService;

    @Autowired
    CourseService courseService;

    @Autowired
    SeminarService seminarService;

    @Autowired
    TeamService teamService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    //////////////////////需要重写
    /*
    @RequestMapping(value="/course/{courseId}/score",method = RequestMethod.GET)
    public List<ScoreVO> listScoreByCourseId(@PathVariable("courseId") BigInteger courseId){
        return scoreService.listAllScoreByCourseId(courseId);
    }
    */

    /**
     * @author hzm
     *根据roundId获得轮次下所有小组的轮次成绩   //教师端
     * @param roundId
     * @return
     */
    @GetMapping("/course/round/{roundId}")
    public List<ScoreVO> listRoundScoreByRoundId(@PathVariable("roundId") String roundId) throws NotFoundException {
        return scoreService.listRoundScoreByRoundId(new BigInteger(roundId));
    }


    /**
     * 获取小组某轮次下所有讨论课的成绩信息  //教师端
     * @param roundId
     * @return
     */
    @GetMapping("/course/round/{roundId}/{teamId}")
    public List<Score> listKlassSeminarScoreByRoundIdAndTeamId(HttpServletRequest request,
                                                               @PathVariable("roundId")String roundId,
                                                               @PathVariable("teamId")String teamId) throws NotFoundException
    {
        List<BigInteger> seminarIdList=seminarService.listSeminarIdByRoundId(new BigInteger(roundId));
        List<BigInteger> klassIdS=teamService.listKlassIdByTeamId(new BigInteger(teamId));  //队伍所属班级ID
        System.out.println("&&"+klassIdS);
        List<SeminarVO> klassSeminarList=new ArrayList<>();
        List<Score> scoreList=new ArrayList<>();
        for(BigInteger item:seminarIdList){
            System.out.println(item);
            List<BigInteger> klassId=seminarService.listKlassIdBySeminarId(item);   //讨论课所属班级ID
            System.out.println("%%"+klassId);
            for(BigInteger klass_1:klassIdS){
                for(BigInteger klass_2:klassId){
                    if(klass_1.equals(klass_2)) {
                        System.out.println(klass_1+"***************"+klass_2);
                        SeminarVO seminarVO=seminarService.getKlassSeminarByKlassIdAndSeminarId(klass_1,item);
                        klassSeminarList.add(seminarVO);
                    }
                }
            }
        }

        for(SeminarVO item:klassSeminarList){
            Score score=scoreService.getKlassSeminarScoreByKlassSeminarIdAndTeamId(item.getKlassSeminarId(),new BigInteger(teamId));
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
     * @return
     */
    @GetMapping("/course/{courseId}/{teamId}/roundInfo")
    public List<Map<String,Object>> listTeamRoundInfoByCourseIdAndTeamId(HttpServletRequest request,
                                                                         @PathVariable("courseId")String courseId,
                                                                         @PathVariable("teamId")String teamId) throws NotFoundException
    {
        return scoreService.listTeamRoundInfoByCourseIdAndTeamId(new BigInteger(courseId),new BigInteger(teamId));
    }

    /**
     * 获取某小组某轮次所有讨论课简单信息
     * @param roundId
     * @return
     */
    @GetMapping("/course/round/{roundId}/team/roundSeminar")
    public List<SeminarSimpleVO> getSeminarByRoundId(HttpServletRequest request,@PathVariable("roundId") String roundId) throws NotFoundException
    {
        BigInteger id=jwtTokenUtil.getIDFromRequest(request);
        List<BigInteger> teamIdList=teamService.listTeamIdByStudentId(id);
        BigInteger courseId=courseService.getCourseIdByRoundId(new BigInteger(roundId));
        BigInteger teamId=new BigInteger("0");
        for(BigInteger teamIdItem:teamIdList){
            BigInteger courseIdItem=teamService.getCourseIdByTeamId(teamIdItem);
            if(courseId.equals(courseIdItem)) teamId=teamIdItem;
        }
        return scoreService.getSeminarByRoundId(new BigInteger(roundId),teamId);
    }


    /**
     *获取某小组某次讨论课成绩  //(简单成绩信息)
     * @param seminarId
     * @return
     */
    @GetMapping("/course/round/team/{seminarId}/seminarScore")
    public Score getTeamSeminarScoreBySeminarId(HttpServletRequest request,@PathVariable("seminarId")String seminarId) throws NotFoundException
    {
        BigInteger id=jwtTokenUtil.getIDFromRequest(request);
        List<BigInteger> teamIdList=teamService.listTeamIdByStudentId(id);
        Seminar seminar=seminarService.getSeminarBySeminarId(new BigInteger(seminarId));
        BigInteger courseId=seminar.getCourseId();
        BigInteger teamId=new BigInteger("0");
        for(BigInteger teamIdItem:teamIdList){
            BigInteger courseIdItem=teamService.getCourseIdByTeamId(teamIdItem);
            if(courseId.equals(courseIdItem)) teamId=teamIdItem;
        }
        return scoreService.getTeamSeminarScoreBySeminarIdAndTeamId(new BigInteger(seminarId),teamId);
    }

}
