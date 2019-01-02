package xmu.ghct.crm.controller;

import com.alibaba.fastjson.support.odps.udf.CodecCheck;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xmu.ghct.crm.VO.ScoreVO;
import xmu.ghct.crm.VO.SeminarVO;
import xmu.ghct.crm.dao.*;
import xmu.ghct.crm.entity.*;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static xmu.ghct.crm.dao.DownloadFileDao.encodeURIComponent;

@RestController
public class PresentationController {

    @Autowired
    PresentationService presentationService;

    @Autowired
    UploadFileService uploadFileService;

    @Autowired
    SeminarService seminarService;

    @Autowired
    DownloadFileDao downloadFileDao;

    @Autowired
    SeminarDao seminarDao;

    @Autowired
    QuestionDao questionDao;

    @Autowired
    ScoreDao scoreDao;

    @Autowired
    CourseService courseService;

    @Autowired
    TotalScoreDao totalScoreDao;

    @Autowired
    ScoreCalculationDao scoreCalculationDao;

    @Autowired
    TeamDao teamDao;

    @Autowired
    TeamService teamService;

    @Autowired
    KlassService klassService;


    /**
     * @author hzm
     * 修改讨论课报名次序
     * @param attendanceId
     * @param orderMap
     * @return
     */
    @PutMapping("attendance/{attendanceId}")
    public boolean updateAttendanceOrderByAttendanceId(@PathVariable("attendanceId") String attendanceId, @RequestBody Map<String,Object> orderMap) throws org.apache.ibatis.javassist.NotFoundException {
        int flag=presentationService.updateAttendanceOrderByAttendanceId(new BigInteger(attendanceId),orderMap);
        if(flag>0) return true;
        else return false;
    }

    /**
     * @author hzm
     * 取消报名
     * @param attendanceId
     * @return
     */
    @DeleteMapping("attendance/{attendanceId}")
    public boolean deleteAttendanceByAttendance(@PathVariable("attendanceId") String attendanceId) throws org.apache.ibatis.javassist.NotFoundException {
        int flag= presentationService.deleteAttendanceByAttendance(new BigInteger(attendanceId));
        if(flag>0) return true;
        else return false;
    }

    /**
     * @author hzm
     * 根据attendanceId上传报告
     * @param attendanceId
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/attendance/{attendanceId}/report",method = RequestMethod.POST)
    public boolean reportUpload(@PathVariable("attendanceId")String attendanceId,@RequestParam("file") MultipartFile file) throws IOException, org.apache.ibatis.javassist.NotFoundException, NotFoundException {
        Attendance attendance=presentationService.getAttendanceByAttendanceId(new BigInteger(attendanceId));
        BigInteger klassSeminarId=attendance.getKlassSeminarId();
        SeminarVO seminarVO =seminarService.getKlassSeminarByKlassSeminarId(klassSeminarId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//修改日期格式
        Date reportDDL=seminarVO.getReportDDL();
        String reportDdl=dateFormat.format(reportDDL);
        Date date=new Date();
        String nowTime = dateFormat.format( date );
        int judge=reportDdl.compareTo(nowTime);
        if(judge<=0) {
            //throw new OutTimeScopeException;
            return false;
        }
        else {
            Map<String, String> reportMap = uploadFileService.uploadFile(file);
            String reportName = reportMap.get("name");
            String reportUrl = reportMap.get("path");
            int flag = presentationService.updateReportByAttendanceId(new BigInteger(attendanceId), reportUrl, reportName);
            if (flag > 0)
                return true;
            else return false;
        }
    }


    /**
     * @author hzm
     * 根据attendanceId下载讨论课报告
     * @param response
     * @param attendanceId
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/attendance/{attendanceId}/report")
    public void reportDownload (HttpServletResponse  response, HttpServletRequest request, @PathVariable("attendanceId")String attendanceId) throws UnsupportedEncodingException, org.apache.ibatis.javassist.NotFoundException {
        System.out.println("下载报告");
        Attendance attendance=presentationService.getAttendanceByAttendanceId(new BigInteger(attendanceId));
        String filePath=attendance.getReportUrl();
        downloadFileDao.downloadFile(response,request,filePath);
    }



    /**
     * @author hzm
     * 根据attendanceId上传ppt
     * @param attendanceId
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/attendance/{attendanceId}/powerPoint",method = RequestMethod.POST)
    public boolean pptUpload(@PathVariable("attendanceId")String attendanceId,@RequestParam("file") MultipartFile file) throws IOException, org.apache.ibatis.javassist.NotFoundException {
            Map<String, String> pptMap = uploadFileService.uploadFile(file);
            String pptName = pptMap.get("name");
            String pptUrl = pptMap.get("path");
            int flag = presentationService.updatePPTByAttendanceId(new BigInteger(attendanceId), pptUrl, pptName);
            if (flag > 0)
                return true;
            else return false;
    }


    /**
     * @author hzm
     * 根据attendanceId下载ppt
     * @param response
     * @param attendanceId
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/attendance/{attendanceId}/powerPoint")
    public void pptDownload (HttpServletResponse  response,HttpServletRequest request,@PathVariable("attendanceId")String attendanceId) throws UnsupportedEncodingException, org.apache.ibatis.javassist.NotFoundException {
        Attendance attendance=presentationService.getAttendanceByAttendanceId(new BigInteger(attendanceId));
        String filePath=attendance.getPptUrl();
        downloadFileDao.downloadFile(response,request,filePath);
    }




    /**
     * @author hzm
     * 批量下载report
     * @param response
     */
    @RequestMapping(value = "/seminar/{seminarId}/klass/{klassId}/report", method = RequestMethod.GET)
    public void multiReportDownload(HttpServletResponse response,HttpServletRequest request,@PathVariable("seminarId") String seminarId,@PathVariable("klassId")String klassId) throws UnsupportedEncodingException, org.apache.ibatis.javassist.NotFoundException, NotFoundException {
        BigInteger klassSeminarId = seminarDao.getKlassSeminarIdBySeminarIdAndKlassId(new BigInteger(seminarId), new BigInteger(klassId));
        List<Attendance> listAttendance = presentationService.listAttendanceByKlassSeminarId(klassSeminarId);
        List<String> names = new ArrayList<String>();
        List<String> paths = new ArrayList<String>();
        for (Attendance item : listAttendance) {
            if(item.getReportName()!=null){
                String reportName=encodeURIComponent(item.getReportUrl());
                names.add(reportName);
                System.out.println(item.getReportName());
            }
            if(item.getReportUrl()!=null){
                String reportUrl=encodeURIComponent(item.getReportUrl());
                paths.add(reportUrl);
                System.out.println(item.getReportUrl());
            }
        }
        downloadFileDao.multiFileDownload(new BigInteger(seminarId),response,request,paths);
    }


    /**
     * @author hzm
     * 批量下载ppt
     * @param response
     */
    @RequestMapping(value = "/seminar/{seminarId}/klass/{klassId}/ppt", method = RequestMethod.GET)
    public void multiPPTDownload(HttpServletResponse response,HttpServletRequest request,@PathVariable("seminarId") String seminarId,@PathVariable("klassId") String klassId) throws UnsupportedEncodingException, org.apache.ibatis.javassist.NotFoundException, NotFoundException {
        BigInteger klassSeminarId = seminarDao.getKlassSeminarIdBySeminarIdAndKlassId(new BigInteger(seminarId), new BigInteger(klassId));
        List<Attendance> listAttendance = presentationService.listAttendanceByKlassSeminarId(klassSeminarId);
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> paths = new ArrayList<>();
        for (Attendance item : listAttendance) {
            names.add(item.getPptName());
            paths.add(item.getPptUrl());
        }
        downloadFileDao.multiFileDownload(new BigInteger(seminarId),response,request,paths);
    }

    /**
     * 修改队伍展示状态，并在讨论课结束时修改讨论课状态
     * @param klassSeminarId
     * @param teamOrder
     */
    @GetMapping("/presentation/{klassSeminarId}/updateStatus")
    public void updatePresentTeam(@PathVariable("klassSeminarId")String klassSeminarId,int teamOrder) throws org.apache.ibatis.javassist.NotFoundException, NotFoundException {
        BigInteger attendanceId=presentationService.getPresentTeam(1);
        int maxTeamOrder=presentationService.selectMaxTeamOrderByKlassSeminarId(new BigInteger(klassSeminarId));
        presentationService.updatePresentByAttendanceId(attendanceId,0);
        while(teamOrder<maxTeamOrder){
            BigInteger attendanceID=presentationService.getAttendanceIdByTeamOrder(teamOrder+1);
            if(attendanceID!=null) {
                presentationService.updatePresentByAttendanceId(attendanceID,1);
                break;
            }
            teamOrder++;
        }
        if(teamOrder==maxTeamOrder){
            seminarService.updateKlassSeminarStatus(new BigInteger(klassSeminarId),2);
        }
    }


    /**
     * 获取报名小组和提问学生信息（展示打分页面）
     * @param klassSeminarId
     * @return
     */
    @GetMapping("/presentation/{klassSeminarId}")
    public List<Map> beingPresentation(@PathVariable("klassSeminarId") String klassSeminarId) throws org.apache.ibatis.javassist.NotFoundException, NotFoundException {
        List<Attendance> attendanceList=presentationService.listAttendanceByKlassSeminarId(new BigInteger(klassSeminarId));
        boolean flag=false;
        BigInteger id=new BigInteger("0");
        for(Attendance item:attendanceList) {
            if (item.getPresent() == 1) {
                flag = true;
                id=item.getAttendanceId();
                break;
            }
        }
        if(flag==false) {
                int minOrder=888;
                for(Attendance item_1:attendanceList){
                    if(item_1.getTeamOrder()<minOrder){minOrder=item_1.getTeamOrder();id=item_1.getAttendanceId();}
                }
                for(Attendance item_1:attendanceList){
                    if(item_1.getAttendanceId()==id) item_1.setPresent(1);}
                System.out.println(id);
                presentationService.updatePresentByAttendanceId(id,new Integer(1));
            }
       // List<Question> questionList=questionDao.listQuestionByKlassSeminarIdAndAttendanceId(new BigInteger(klassSeminarId),id);
        //presentationMap.put("questionList",questionList);
        List<Map> maps=new ArrayList<>();
        for(Attendance item:attendanceList){
            Map<String,Object> presentationMap=new HashMap<>();
            presentationMap.put("attendanceId",item.getAttendanceId());
            presentationMap.put("teamId",item.getTeamId());
            presentationMap.put("klassSeminarId",item.getKlassSeminarId());
            presentationMap.put("teamOrder",item.getTeamOrder());
            presentationMap.put("present",item.getPresent());
            Team team=teamService.getTeamInfoByTeamId(item.getTeamId());
            presentationMap.put("teamSerial",team.getKlassSerial());
            Klass klass=klassService.getKlassByKlassId(team.getKlassId());
            presentationMap.put("klassSerial",klass.getKlassSerial());
            maps.add(presentationMap);
        }
        return maps;
    }

    /**
     * 修改展示成绩(需要展示现有成绩)
     * @param klassSeminarId
     * @param teamId
     * @return
     */
    @GetMapping("/presentation/{klassSeminarId}/{teamId}/modifyScore")
    public Score modifyScore(@PathVariable("klassSeminarId") String klassSeminarId,@PathVariable("teamId")String teamId) throws NotFoundException {
        Score score=scoreDao.getSeminarScoreByKlassSeminarIdAndTeamId(new BigInteger(klassSeminarId),new BigInteger(teamId));
        return score;
    }

    /**
     * 修改展示成绩（展示给分）
     * @param klassSeminarId
     * @param teamId
     * @param presentationScoreMap
     * @return
     */
    @PutMapping("/presentation/{klassSeminarId}/attendance/{teamId}")
    public boolean updatePresentationScore(@PathVariable("klassSeminarId") String klassSeminarId,@PathVariable("teamId")String teamId,
                                           @RequestBody  Map<String,Object> presentationScoreMap) throws NotFoundException{
        double presentationScore=new Double(presentationScoreMap.get("presentationScore").toString());
        Score score=scoreDao.getSeminarScoreByKlassSeminarIdAndTeamId(new BigInteger(klassSeminarId),new BigInteger(teamId));
        score.setPresentationScore(presentationScore);
        SeminarVO seminarVO=seminarService.getKlassSeminarByKlassSeminarId(new BigInteger(klassSeminarId));
        BigInteger courseId=courseService.getCourseIdByKlassId(seminarVO.getKlassId());
        Score totalScore=totalScoreDao.totalScoreCalculation(score,courseId);
        int flag=scoreDao.updateSeminarScoreBySeminarIdAndTeamId(totalScore);
        BigInteger roundId=seminarService.getRoundIdBySeminarId(seminarVO.getSeminarId());
        Score score1=scoreCalculationDao.roundScoreCalculation(totalScore,roundId,new BigInteger(teamId),courseId);
        ScoreVO scoreVO=new ScoreVO();
        scoreVO.setTotalScore(score1.getTotalScore());
        scoreVO.setReportScore(score1.getReportScore());
        scoreVO.setQuestionScore(score1.getQuestionScore());
        scoreVO.setPresentationScore(score1.getPresentationScore());
        scoreVO.setRoundId(roundId);
        scoreVO.setTeamId(new BigInteger(teamId));
        scoreDao.updateRoundScoreByRoundIdAndTeamId(scoreVO);
        if(flag>0) return true;
        else return false;
    }


    /**
     * 报名讨论课
     * @param klassSeminarId
     * @param teamId
     * @param attendanceMap
     * @return
     */
    //需要teamId，但是应该是根据jwt获得，所以这里teamId用于测试用
    @RequestMapping(value="/seminar/{klassSeminarId}/attendance" ,method = RequestMethod.POST)
    public boolean attendanceSeminar(@PathVariable("klassSeminarId")String klassSeminarId,@RequestParam("teamId") String teamId,@RequestBody Map<String,Object> attendanceMap) throws org.apache.ibatis.javassist.NotFoundException, SQLException, NotFoundException {
        SeminarVO seminarVO=seminarDao.getKlassSeminarByKlassSeminarId(new BigInteger(klassSeminarId));
        Seminar seminar=seminarDao.getSeminarBySeminarId(seminarVO.getSeminarId());
        List<Attendance> attendanceList=presentationService.listAttendanceByKlassSeminarId(new BigInteger(klassSeminarId));
        for(Attendance attendance:attendanceList){
            if(new BigInteger(teamId)==attendance.getTeamId()) return false;//该队伍已报名讨论课
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//修改日期格式
        String enrollEndTime=dateFormat.format(seminar.getEnrollEndTime());
        String enrollStartTime=dateFormat.format(seminar.getEnrollStartTime());
        Date date=new Date();
        String nowTime = dateFormat.format( date );
        int startJudge=enrollStartTime.compareTo(nowTime);
        int endJudge=enrollEndTime.compareTo(nowTime);
        if(startJudge>0||endJudge<0) {
            //throw new OutTimeScopeException;
            return false;
        }
        else{
            int flag= presentationService.insertAttendance(new BigInteger(klassSeminarId),new BigInteger(teamId),attendanceMap);
            if(flag>0) return true;
            else return false;
        }
    }


    /**
     * @author hzm
     * 获取讨论课小组的班级讨论课信息(包括报名、未报名等各种情况)
     * @param klassSeminarId
     * @param teamId
     * @return
     */
    @GetMapping("/seminar/{klassSeminarId}/{teamId}/seminarInfo")
    public Map<String,Object> getTeamKlassSeminarInfoByKlassSeminarIdAndTeamId(@PathVariable("klassSeminarId")String klassSeminarId,@PathVariable("teamId")String teamId) throws NotFoundException, org.apache.ibatis.javassist.NotFoundException {
             return presentationService.getTeamKlassSeminarInfoByKlassSeminarIdAndTeamId(new BigInteger(klassSeminarId),new BigInteger(teamId));
    }


    /**
     * @author hzm
     * 修改讨论课报名并返回新的报名信息
     * @param attendanceId
     * @return
     */
    @GetMapping("/klassSeminar/attendance/{attendanceId}/modifyAttendance")
    public List<Map> modifyAttendanceByAttendanceId(@PathVariable("attendanceId")String attendanceId,@RequestBody Map<String,String> orderMap) throws NotFoundException, org.apache.ibatis.javassist.NotFoundException {
        return presentationService.modifyAttendanceByAttendanceId(new BigInteger(attendanceId),orderMap);
    }

}
