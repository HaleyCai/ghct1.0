package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.*;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.entity.Seminar;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.security.JwtTokenUtil;
import xmu.ghct.crm.service.KlassService;
import xmu.ghct.crm.service.ScoreService;
import xmu.ghct.crm.service.SeminarService;
import xmu.ghct.crm.service.TeamService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
public class SeminarController {

    @Autowired
    private SeminarService seminarService;

    @Autowired
    ScoreService scoreService;

    @Autowired
    KlassService klassService;

    @Autowired
    TeamService teamService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;
    /**
     * 根据轮次id获取该轮次下所有的讨论课的简略信息
     * @param roundId
     * @return
     */
    @RequestMapping(value="/round/{roundId}/seminar",method = RequestMethod.GET)
    @ResponseBody
    public List<SeminarSimpleVO> getSeminarByRoundId(@PathVariable("roundId") String roundId) throws NotFoundException {
        return seminarService.getSeminarByRoundId(new BigInteger(roundId));
    }

    /**
     * @author hzm
     * 创建讨论课
     * @param seminarMap
     * @param courseId
     * @return
     * @throws ParseException
     */
    @RequestMapping(value="/course/{courseId}/seminar/creatSeminar",method = RequestMethod.POST)
    public Boolean creatSeminar(@PathVariable("courseId") String courseId,
                                @RequestBody Map<String,Object> seminarMap) throws ParseException, NotFoundException, SQLException {
        int flag=seminarService.creatSeminar(new BigInteger(courseId),seminarMap);
        if(flag>0) return true;
        else  return false;
    }

    /**
     * 根据讨论课ID获取讨论课所属班级简单信息
     * @param seminarId
     * @return
     */
    @RequestMapping(value="/seminar/{seminarId}/klass",method = RequestMethod.GET)
    public List<Klass> listKlassSeminarBySeminarId(@PathVariable("seminarId") String seminarId) throws NotFoundException {
        return klassService.listKlassBySeminarId(new BigInteger(seminarId));
    }

    /**
     *修改讨论课信息
     * @param seminarId
     * @param seminarMap
     * @return
     * @throws ParseException
     */
    @RequestMapping(value="/seminar/{seminarId}",method = RequestMethod.PUT)
    public boolean updateSeminarBySeminarId(@PathVariable("seminarId")String seminarId,
                                            @RequestBody Map<String,Object> seminarMap) throws ParseException, NotFoundException {
        int flag=seminarService.updateSeminarBySeminarId(new BigInteger(seminarId),seminarMap);
        if(flag>0) return true;
        else return false;
    }

    /**
     *删除讨论课
     * @param seminarId
     * @return
     */
    @RequestMapping(value="/seminar/{seminarId}",method = RequestMethod.DELETE)
    public boolean deleteSeminarBySeminarId(@PathVariable("seminarId") String seminarId) throws NotFoundException
    {
        int flag_1=seminarService.deleteSeminarBySeminarId(new BigInteger(seminarId));
        int flag_2=seminarService.deleteKlassSeminarBySeminarId(new BigInteger(seminarId));
        int flag_3=scoreService.deleteSeminarScoreBySeminarId(new BigInteger(seminarId));
        if(flag_1>0&&flag_2>0&&flag_3>0) return true;
        else return false;
    }

    /**
     * 获取讨论课信息
     * @param seminarId
     * @return
     */
    @RequestMapping(value="/seminar/{seminarId}",method = RequestMethod.GET)
    public  Seminar getSeminarBySeminarId(@PathVariable("seminarId") String seminarId) throws NotFoundException {
        return seminarService.getSeminarBySeminarId(new BigInteger(seminarId));
    }

    /**
     * 修改班级讨论课信息（修改报告截止时间）
     * @param klassSeminarId
     * @param klassMap
     * @return
     * @throws ParseException
     */
    @PutMapping("/seminar/{klassSeminarId}/updateReportDDL")
    public boolean updateKlassSeminarByKlassSeminarId(@PathVariable("klassSeminarId") String klassSeminarId,
                                                      @RequestBody Map<String,Object> klassMap) throws ParseException, NotFoundException, org.apache.ibatis.javassist.NotFoundException {
        int flag=seminarService.updateKlassSeminarByKlassSeminarId(
                new BigInteger(klassSeminarId),
                klassMap);
        if(flag>0) return true;
        else return  false;
    }


    /**
     * 删除班级讨论课
     * @param klassId
     * @param seminarId
     * @return
     */
    @DeleteMapping("/seminar/{seminarId}/klass/{klassId}")
    public boolean deleteKlassSeminarBySeminarIdAndKlassId(@PathVariable("seminarId") String seminarId,
                                                           @PathVariable("klassId") String klassId) throws NotFoundException {
        int flag=seminarService.deleteKlassSeminarBySeminarIdAndKlassId(
                new BigInteger(klassId),
                new BigInteger(seminarId));
        if(flag>0) return true;
        else return false;
    }


    /**
     * 获取班级讨论课完整信息（包括讨论课的所有信息）
     * @param klassId
     * @param seminarId
     * @return
     */
    @GetMapping("seminar/{seminarId}/klass/{klassId}")
    public SeminarVO getKlassSeminarByKlassIdAndSeminarId(@PathVariable("seminarId") String seminarId,
                                                          @PathVariable("klassId") String klassId) throws NotFoundException {
        return seminarService.getKlassSeminarByKlassIdAndSeminarId(new BigInteger(klassId),new BigInteger(seminarId));

    }


    /**
     * 修改班级讨论课进行时状态
     * @param klassSeminarId
     * @param status
     * @return
     */
    @PutMapping("presentation/{klassSeminarId}/status")
    public boolean updateKlassSeminarStatus(@PathVariable("klassSeminarId") String klassSeminarId,@RequestParam int status) throws NotFoundException {
        int flag=seminarService.updateKlassSeminarStatus(new BigInteger(klassSeminarId),status);
        if(flag>0)return true;
        else return  false;
    }


    /**
     * 获得某一队伍的某次讨论课成绩   //学生端查看某次讨论课成绩
     * @param seminarId
     * @return
     */
    @GetMapping("seminar/{seminarId}/team/seminarScore")
    public SeminarScoreVO getTeamSeminarScoreByTeamIdAndSeminarId(HttpServletRequest request,
                                                                  @PathVariable("seminarId") String seminarId) throws NotFoundException {

        BigInteger id=jwtTokenUtil.getIDFromRequest(request);
        List<BigInteger> teamIdList=teamService.listTeamIdByStudentId(id);
        Seminar seminar=seminarService.getSeminarBySeminarId(new BigInteger(seminarId));
        BigInteger courseId=seminar.getCourseId();
        BigInteger teamId=new BigInteger("0");
        for(BigInteger teamIdItem:teamIdList){
            System.out.println(teamIdItem);
            BigInteger courseIdItem=teamService.getCourseIdByTeamId(teamIdItem);
            if(courseId.equals(courseIdItem)) teamId=teamIdItem;
        }
        return seminarService.getTeamSeminarScoreByTeamIdAndSeminarId(teamId,new BigInteger(seminarId));
    }

    /**
     * 修改某一队伍某次讨论课的成绩
     * @param seminarId
     * @param teamId
     * @param seminarScoreMap
     * @return
     */
    @PutMapping("seminar/{seminarId}/team/{teamId}/modifySeminarScore")
    public boolean updateSeminarScoreBySeminarIdAndTeamId(@PathVariable("seminarId") String seminarId,
                                                          @PathVariable("teamId") String teamId,@RequestBody Map<String,Object> seminarScoreMap) throws NotFoundException {
        int flag=seminarService.updateSeminarScoreBySeminarIdAndTeamId(new BigInteger(seminarId),new BigInteger(teamId),seminarScoreMap);
        if(flag>0) return true;
        else return false;
    }


    /**
     *  获取班级讨论课的所有队伍的成绩
     * @param klassSeminarId
     * @return
     * @throws NotFoundException
     * @throws org.apache.ibatis.javassist.NotFoundException
     */
    @GetMapping("seminar/{klassSeminarId}/seminarScore")
    public List<SeminarScoreVO> listKlassSeminarScoreByKlassIdAndSeminarId(@PathVariable("klassSeminarId") BigInteger klassSeminarId) throws NotFoundException, org.apache.ibatis.javassist.NotFoundException {
        return  seminarService.listKlassSeminarScoreByKlassIdAndSeminarId(klassSeminarId);
    }


    /**
     * 教师给报告成绩
     * @param klassSeminarId
     * @param reportMapList
     * @return
     */
    @PostMapping("seminar/{klassSeminarId}/updateReportScore")
    public boolean updateReportScoreByKlassSeminarId(@PathVariable("klassSeminarId") String klassSeminarId,@RequestBody List<Map> reportMapList) throws NotFoundException{
        return seminarService.updateReportScoreByKlassSeminarId(new BigInteger(klassSeminarId),reportMapList);
    }


    /**
     * @author hzm
     * 获取班级讨论课报名小组上传的报告信息
     * @param klassSeminarId
     * @return
     */
    @GetMapping("/seminar/{klassSeminarId}/reportSubmitStatus")
    public List<Map> listReportUploadByKlassSeminarId(@PathVariable("klassSeminarId") String klassSeminarId) throws org.apache.ibatis.javassist.NotFoundException, NotFoundException {
        return seminarService.listFileUploadStatusByKlassSeminarId(new BigInteger(klassSeminarId));
    }


    /**
     * @author hzm
     * 获取班级讨论课ppt提交情况
     * @param klassSeminarId
     * @return
     */
    @GetMapping("/seminar/{klassSeminarId}/pptUploadStatus")
    public List<Map> listPPTUploadStatusByKlassSeminarId(@PathVariable("klassSeminarId") String klassSeminarId) throws org.apache.ibatis.javassist.NotFoundException, NotFoundException {
        return seminarService.listFileUploadStatusByKlassSeminarId(new BigInteger(klassSeminarId));
    }


    /**
     * @author hzm
     *获取班级讨论课的报名信息及报名小组ppt提交情况
     * @param klassSeminarId
     * @return
     */
    @GetMapping("/round/seminar/{klassSeminarId}/attendance")
    public List<Map> listStudentKlassSeminarByKlassSeminarId(HttpServletRequest request, @PathVariable("klassSeminarId") String klassSeminarId) throws NotFoundException, org.apache.ibatis.javassist.NotFoundException {
             return seminarService.listStudentKlassSeminarByKlassSeminarId(request,new BigInteger(klassSeminarId));
    }

}
