package xmu.ghct.crm.controller;

import xmu.ghct.crm.dao.ShareDao;
import xmu.ghct.crm.entity.Share;
import xmu.ghct.crm.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.vo.ScoreVO;
import xmu.ghct.crm.vo.SeminarSimpleVO;
import xmu.ghct.crm.entity.Score;
import xmu.ghct.crm.security.JwtTokenUtil;
import xmu.ghct.crm.service.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author hzm,caiyq
 */
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
    KlassService klassService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    ShareDao shareDao;

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
    public List<Score> listKlassSeminarScoreByRoundIdAndTeamId(@PathVariable("roundId")String roundId,
                                                               @PathVariable("teamId")String teamId) throws NotFoundException
    {
        System.out.println("roundId"+roundId);
        System.out.println("teamId"+teamId);
        return scoreService.listKlassSeminarScoreByRoundIdAndTeamId(new BigInteger(roundId),new BigInteger(teamId));
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
        System.out.println("teamIdList "+teamIdList);
        BigInteger courseId=courseService.getCourseIdByRoundId(new BigInteger(roundId));
        BigInteger teamId=new BigInteger("0");

        Share share=shareDao.getSubTeamShare(courseId);
        System.out.println("share "+share);
        BigInteger mainCourseId=new BigInteger("0");
        if(share!=null) {
            mainCourseId=share.getMainCourseId();
        }
        else{
            System.out.println("in else");
            mainCourseId=courseId;
        }
        System.out.println("mainCourseId "+mainCourseId);

        for(BigInteger teamIdItem:teamIdList){
            BigInteger courseIdItem=teamService.getCourseIdByTeamId(teamIdItem);
            if(mainCourseId.equals(courseIdItem)) {
                teamId=teamIdItem;break;
            }
        }
        System.out.println("teamId "+teamId);
        return scoreService.getSeminarByRoundId(new BigInteger(roundId),teamId);
    }

    /**
     *获取某小组某次讨论课成绩  //(简单成绩信息)
     * 根据klassSeminarId查本次讨论课，学生用户所在队伍的成绩
     * *************
     * @param klassSeminarId
     * @return
     */
    @GetMapping("/course/round/team/{klassSeminarId}/seminarScore")
    public Score getTeamSeminarScoreBySeminarId(HttpServletRequest request,
                                                @PathVariable("klassSeminarId")String klassSeminarId) throws NotFoundException
    {
        BigInteger id=jwtTokenUtil.getIDFromRequest(request);
        List<BigInteger> teamIdList=teamService.listTeamIdByStudentId(id);
        BigInteger klassId=seminarService.getKlassIdByKlassSeminarId(new BigInteger(klassSeminarId));
        BigInteger teamId=new BigInteger("0");
        for(BigInteger teamIdItem:teamIdList)
        {
            ///找到该队伍的班级
            List<BigInteger> klassIds=teamService.listKlassIdByTeamId(teamIdItem);
            System.out.println("队伍所有klass："+klassIds);
            for(BigInteger klassId2:klassIds) {
                if(klassId.equals(klassId2))
                {
                    teamId=teamIdItem;
                }
            }
        }
        System.out.println("teamId "+teamId);
        return scoreService.getTeamSeminarScoreByKlassSeminarIdAndTeamId(new BigInteger(klassSeminarId),teamId);
    }
}
