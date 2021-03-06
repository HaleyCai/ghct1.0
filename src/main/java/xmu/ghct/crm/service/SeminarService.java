package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import xmu.ghct.crm.vo.*;
import xmu.ghct.crm.dao.*;
import xmu.ghct.crm.entity.*;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.mapper.ScoreMapper;
import xmu.ghct.crm.mapper.SeminarMapper;
import xmu.ghct.crm.security.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

/**
 * @author hzm
 */
@Service
public class SeminarService {
    @Autowired
    SeminarDao seminarDao;

    @Autowired
    RoundDao roundDao;

    @Autowired
    PresentationDao presentationDao;

    @Autowired
    KlassDao klassDao;

    @Autowired
    DateDao dateDao;

    @Autowired
    TeamDao teamDao;

    @Autowired
    ScoreMapper scoreMapper;

    @Autowired
    ScoreDao scoreDao;

    @Autowired
    CourseDao courseDao;

    @Autowired
    ShareDao shareDao;

    @Autowired
    ScoreCalculationDao scoreCalculationDao;

    @Autowired
    SeminarMapper seminarMapper;

    @Autowired
    TotalScoreDao totalScoreDao;

    @Autowired
    CourseService courseService;


    @Autowired
    JwtTokenUtil jwtTokenUtil;

    private static final String NOTCHOICE="0";

    public int creatSeminar(BigInteger courseId,Map<String,Object> seminarMap) throws ParseException, NotFoundException, SQLException {
        Seminar seminar=new Seminar();
        seminar.setCourseId(courseId);
        if(NOTCHOICE.equals(seminarMap.get("roundId").toString())){
            System.out.println("讨论课轮次默认为0");
            Round round=new Round();
            round.setCourseId(courseId);
            round.setPresentationScoreMethod(1);
            round.setQuestionScoreMethod(0);
            round.setReportScoreMethod(1);
            //默认创建，序号为最大序号+1
            round.setRoundSerial(roundDao.getNewRoundNum(round.getCourseId()));
            roundDao.insertRound(round,courseId);
            System.out.println("roundId"+round.getRoundId());
            seminar.setRoundId(round.getRoundId());
            //默认创建每个班每轮允许报名次数为1
            roundDao.defaultEnrollNumber(seminar.getCourseId(),seminar.getRoundId());
        }else{
            seminar.setRoundId(new BigInteger(seminarMap.get("roundId").toString()));
        }
        seminar.setSeminarName(seminarMap.get("seminarName").toString());
        seminar.setIntroduction(seminarMap.get("introduction").toString());
        seminar.setMaxTeam(new Integer(seminarMap.get("maxTeam").toString()));
        seminar.setVisible(new Integer(seminarMap.get("visible").toString()));
        //系统自动生成序号
        seminar.setSeminarSerial(seminarDao.createNewSeminarSeminarSerial(courseId));
        Date start = dateDao.transferToDateTime(seminarMap.get("enrollStartTime").toString());
        seminar.setEnrollStartTime(start);
        Date end = dateDao.transferToDateTime(seminarMap.get("enrollEndTime").toString());
        seminar.setEnrollEndTime(end);
        int flag= seminarDao.creatSeminar(seminar);
        List<Klass> klassList=klassDao.listKlassByCourseId(courseId);
        for(Klass item:klassList){
            List<Team> teamList=teamDao.listTeamByKlassId(item.getKlassId());
            seminarDao.insertKlassSeminarBySeminarIdAndKlassId(seminar.getSeminarId(),item.getKlassId());
            BigInteger klassSeminarId=seminarDao.getKlassSeminarIdBySeminarIdAndKlassId(seminar.getSeminarId(),item.getKlassId());
            for(Team team:teamList){
                scoreDao.insertSeminarScore(klassSeminarId,team.getTeamId());
            }
        }
        return flag;
    }


    /**
     * 获取某轮次下所有讨论课的简略信息  学生
     * @param roundId
     * @return
     */
    public List<SeminarSimpleVO> getSeminarByRoundId(BigInteger klassId,BigInteger roundId) throws NotFoundException {
        List<SeminarSimpleVO> list = roundDao.getSeminarByRoundId(roundId);
        for(SeminarSimpleVO item:list){
            SeminarVO seminarVO=seminarDao.getKlassSeminarByKlassIdAndSeminarId(klassId,item.getId());
            item.setKlassSeminarId(seminarVO.getKlassSeminarId());
        }
        if(list==null||list.isEmpty())
        {
            return null;
        }
        return list;
    }

    /**
     * 获取某轮次下所有讨论课的简略信息  教师
     * @param roundId
     * @return
     */
    public List<SeminarSimpleVO> getTeacherSeminarByRoundId(BigInteger roundId) throws NotFoundException {
        List<SeminarSimpleVO> list = roundDao.getSeminarByRoundId(roundId);
        if(list==null||list.isEmpty())
        {
            throw new NotFoundException("未查找到该轮次下的讨论课信息！");
        }
        return list;
    }


    /**
     * 根据seminarId修改讨论课信息
     * @param seminarId
     * @return
     */
    public int updateSeminarBySeminarId(BigInteger seminarId,@RequestBody Map<String,Object> seminarMap) throws ParseException, NotFoundException {
        Seminar seminar=new Seminar();
        Date enrollStartTime = dateDao.transferToDateTime(seminarMap.get("enrollStartTime").toString());
        seminar.setEnrollStartTime(enrollStartTime);
        Date enrollEndTime = dateDao.transferToDateTime(seminarMap.get("enrollEndTime").toString());
        seminar.setEnrollEndTime(enrollEndTime);
        seminar.setIntroduction(seminarMap.get("introduction").toString());
        seminar.setMaxTeam(new Integer(seminarMap.get("maxTeam").toString()));
        seminar.setRoundId(new BigInteger(seminarMap.get("roundId").toString()));
        seminar.setSeminarName(seminarMap.get("seminarName").toString());
        seminar.setSeminarId(seminarId);
        seminar.setVisible(new Integer(seminarMap.get("visible").toString()));
        return seminarDao.updateSeminarBySeminarId(seminar);
    }


    public int deleteSeminarBySeminarId(BigInteger seminarId) throws NotFoundException {
        return seminarDao.deleteSeminarBySeminarId(seminarId);
    }

    public int deleteKlassSeminarBySeminarId(BigInteger seminarId){
        return seminarDao.deleteKlassSeminarBySeminarId(seminarId);
    }

    public  Seminar getSeminarBySeminarId(BigInteger seminarId) throws NotFoundException {
        return seminarDao.getSeminarBySeminarId(seminarId);
    }

    public int updateKlassSeminarByKlassSeminarId(BigInteger klassSeminarId,Map<String,Object> seminarMap) throws ParseException, NotFoundException, org.apache.ibatis.javassist.NotFoundException {
        Date reportDDL = dateDao.transferToDateTime(seminarMap.get("reportDDL").toString());
        //修改最后一组展示的状态
        presentationDao.updatePresentByAttendanceId(new BigInteger(seminarMap.get("lastAttendanceId").toString()),2);
        return seminarDao.updateKlassSeminarByKlassSeminarId(klassSeminarId,reportDDL);
    }



    public int deleteKlassSeminarBySeminarIdAndKlassId(BigInteger klassId,BigInteger seminarId) throws NotFoundException {
        return seminarDao.deleteKlassSeminarBySeminarIdAndKlassId(klassId,seminarId);
    }

    /**
     * @author hzm
     * 根据seminarId和klassId获得班级讨论课信息
     * @param klassId
     * @param seminarId
     * @return
     */
    public SeminarVO getKlassSeminarByKlassIdAndSeminarId(BigInteger klassId, BigInteger seminarId) throws NotFoundException {
        BigInteger klassSeminarId=seminarMapper.getKlassSeminarIdBySeminarIdAndKlassId(seminarId,klassId);
        SeminarVO seminarVO=seminarDao.getKlassSeminarByKlassIdAndSeminarId(klassId,seminarId);
        Seminar seminar=seminarDao.getSeminarBySeminarId(seminarId);
        System.out.println("seminar "+seminar);
        Integer roundSerial=roundDao.getRoundSerialByRoundId(seminar.getRoundId());
        seminarVO.setKlassSerial(klassDao.getKlassSerialByKlassId(klassId));
        seminarVO.setRoundSerial(roundSerial);
        seminarVO.setRoundId(seminar.getRoundId());
        seminarVO.setEnrollEndTime(seminar.getEnrollEndTime());
        seminarVO.setEnrollStartTime(seminar.getEnrollStartTime());
        seminarVO.setSeminarName(seminar.getSeminarName());
        seminarVO.setMaxTeam(seminar.getMaxTeam());
        seminarVO.setIntroduction(seminar.getIntroduction());
        seminarVO.setSeminarId(seminarId);
        seminarVO.setSeminarSerial(seminar.getSeminarSerial());
        seminarVO.setKlassSeminarId(klassSeminarId);
        return seminarVO;
    }



    /**
     * @author hzm
     * 修改班级讨论课状态
     * @param klassSeminarId
     * @param status
     * @return
     */
    public int updateKlassSeminarStatus(BigInteger klassSeminarId,int status) throws NotFoundException {
        return seminarDao.updateKlassSeminarStatus(klassSeminarId,status);
    }

    public SeminarScoreVO getTeamSeminarScoreByTeamIdAndSeminarId(BigInteger teamId, BigInteger seminarId) throws NotFoundException {
        SeminarScoreVO seminarScoreVO=new SeminarScoreVO();
        Team teamInfo=teamDao.getTeamInfoByTeamId(teamId);
        if(teamInfo==null) {
            return null;
        }
        BigInteger klassSeminarId=seminarDao.getKlassSeminarIdBySeminarIdAndKlassId(seminarId,teamInfo.getKlassId());
        Seminar seminarInfo=seminarDao.getSeminarBySeminarId(seminarId);
        Klass klassInfo=klassDao.getKlassByKlassId(teamInfo.getKlassId());
        Score seminarScore=scoreMapper.getSeminarScoreByKlassSeminarIdAndTeamId(klassSeminarId,teamId);
        System.out.println(seminarScore);
        seminarScoreVO.setKlassSeminarId(klassSeminarId);
        seminarScoreVO.setTeamName(teamInfo.getTeamName());
        seminarScoreVO.setSeminarId(seminarId);
        seminarScoreVO.setTeamId(teamInfo.getTeamId());
        seminarScoreVO.setTeamSerial(teamInfo.getTeamSerial());
        seminarScoreVO.setIntroduction(seminarInfo.getIntroduction());
        seminarScoreVO.setSeminarName(seminarInfo.getSeminarName());
        seminarScoreVO.setGrade(klassInfo.getGrade());
        seminarScoreVO.setKlassSerial(klassInfo.getKlassSerial());
        seminarScoreVO.setSeminarSerial(seminarInfo.getSeminarSerial());
        seminarScoreVO.setPresentationScore(seminarScore.getPresentationScore());
        seminarScoreVO.setQuestionScore(seminarScore.getQuestionScore());
        seminarScoreVO.setReportScore(seminarScore.getReportScore());
        seminarScoreVO.setTotalScore(seminarScore.getTotalScore());
        return  seminarScoreVO;
    }


    /**
     * @author hzm
     * 更新讨论课成绩
     * @param klassSeminarId
     * @param teamId
     * @param seminarScoreMap
     * @return
     */
    public int updateSeminarScoreBySeminarIdAndTeamId(BigInteger klassSeminarId, BigInteger teamId, Map<String,Object> seminarScoreMap) throws NotFoundException {
        Score score=new Score();
        BigInteger seminarId=seminarMapper.getKlassSeminarByKlassSeminarId(klassSeminarId).getSeminarId();
        BigInteger roundId=seminarMapper.getRoundIdBySeminarId(seminarId);
        BigInteger courseId=courseDao.getCourseIdByTeamId(teamId);
        Course course=courseDao.getCourseByCourseId(courseId);

        Double presentationScore;
        String presentation=seminarScoreMap.get("presentationScore").toString();
        if(presentation==null) {
            presentationScore=0.0;
        }
        presentationScore=new Double(presentation);

        Double questionScore;
        String question=seminarScoreMap.get("questionScore").toString();
        if(question==null) {
            questionScore=0.0;
        }
        questionScore=new Double(question);

        Double reportScore;
        String report=seminarScoreMap.get("reportScore").toString();
        if(report==null) {
            reportScore=0.0;
        }
        reportScore=new Double(report);
        score.setTeamId(teamId);
        score.setKlassSeminarId(klassSeminarId);
        score.setPresentationScore(presentationScore);
        score.setQuestionScore(questionScore);
        score.setReportScore(reportScore);
        double totalScore=questionScore*(course.getPresentationPercentage()*0.01)+questionScore*(course.getQuestionPercentage()*0.01)+
                          reportScore*(course.getReportPercentage()*0.01);
        score.setTotalScore(totalScore);
        int flag=scoreDao.updateSeminarScoreBySeminarIdAndTeamId(score);
        ScoreVO roundScoreVO=new ScoreVO();
        Score roundScore=scoreCalculationDao.roundScoreCalculation(score,roundId,teamId,courseId);
        roundScoreVO.setPresentationScore(roundScore.getPresentationScore());
        roundScoreVO.setQuestionScore(roundScore.getQuestionScore());
        roundScoreVO.setReportScore(roundScore.getReportScore());
        roundScoreVO.setTotalScore(roundScore.getTotalScore());
        roundScoreVO.setRoundId(roundId);
        roundScoreVO.setTeamId(teamId);
        scoreMapper.updateRoundScoreByRoundIdAndTeamId(
                roundScoreVO.getRoundId(),
                roundScoreVO.getTeamId(),
                new BigDecimal(roundScoreVO.getPresentationScore()),
                new BigDecimal(roundScoreVO.getQuestionScore()),
                new BigDecimal(roundScoreVO.getReportScore()),
                new BigDecimal(roundScoreVO.getTotalScore()));
        return flag;
    }


    /**
     * 根据klassId 和seminarId获得班级讨论课所有队伍成绩
     * @param klassSeminarId
     * @return
     * @throws NotFoundException
     * @throws org.apache.ibatis.javassist.NotFoundException
     */
    public List<SeminarScoreVO> listKlassSeminarScoreByKlassIdAndSeminarId(BigInteger klassSeminarId) throws NotFoundException, org.apache.ibatis.javassist.NotFoundException {
        List<Attendance> attendanceList=presentationDao.listAttendanceByKlassSeminarId(klassSeminarId);
        List<Team> teamList=new ArrayList<>();
        for(Attendance attendance:attendanceList){
            Team team=teamDao.getTeamInfoByTeamId(attendance.getTeamId());
            if(team==null){
                continue;
            }
            else{
                teamList.add(team);
            }
        }
        SeminarVO seminarVO=seminarDao.getKlassSeminarByKlassSeminarId(klassSeminarId);
        BigInteger klassId=seminarVO.getKlassId();
        Klass klass=klassDao.getKlassByKlassId(klassId);
        Seminar seminar=seminarDao.getSeminarBySeminarId(seminarVO.getSeminarId());
        List<SeminarScoreVO> seminarScoreVOList=new ArrayList<>();
        for(Team item:teamList){
            SeminarScoreVO seminarScoreVO=new SeminarScoreVO();
            seminarScoreVO.setTeamId(item.getTeamId());
            seminarScoreVO.setKlassSerial(klass.getKlassSerial());
            seminarScoreVO.setSeminarName(seminar.getSeminarName());
            seminarScoreVO.setTeamSerial(item.getTeamSerial());
            Score score=scoreMapper.getSeminarScoreByKlassSeminarIdAndTeamId(klassSeminarId,item.getTeamId());
            seminarScoreVO.setPresentationScore(score.getPresentationScore());
            seminarScoreVO.setQuestionScore(score.getQuestionScore());
            seminarScoreVO.setReportScore(score.getReportScore());
            seminarScoreVO.setTotalScore(score.getTotalScore());
            seminarScoreVOList.add(seminarScoreVO);
        }
        return seminarScoreVOList;
    }


    public boolean updateReportScoreByKlassSeminarId(BigInteger klassSeminarId,ScoreVO scoreVO) throws NotFoundException{
        List<SeminarScoreVO> scoreList=scoreVO.getScoreList();
        System.out.println("scoreList "+scoreList);
        if(scoreList!=null)
        {
            for(SeminarScoreVO scoreItem:scoreList)
            {
                BigInteger teamId=scoreItem.getTeamId();
                BigInteger courseId=teamDao.getCourseIdByTeamId(teamId);
                BigInteger seminarId=seminarDao.getKlassSeminarByKlassSeminarId(klassSeminarId).getSeminarId();
                BigInteger roundId=seminarDao.getRoundIdBySeminarId(seminarId);
                Score score=scoreDao.getSeminarScoreByKlassSeminarIdAndTeamId(klassSeminarId,teamId);
                Double reportScore=scoreItem.getReportScore();
                score.setReportScore(reportScore);
                Score seminarScore=totalScoreDao.totalScoreCalculation(score,courseId);
                int flag=scoreDao.updateSeminarScoreBySeminarIdAndTeamId(seminarScore);
                Score roundScore=scoreCalculationDao.roundScoreCalculation(seminarScore,roundId,teamId,courseId);
                ScoreVO roundScoreVO=new ScoreVO();
                roundScoreVO.setPresentationScore(roundScore.getPresentationScore());
                roundScoreVO.setQuestionScore(roundScore.getQuestionScore());
                roundScoreVO.setReportScore(roundScore.getReportScore());
                roundScoreVO.setTotalScore(roundScore.getTotalScore());
                roundScoreVO.setRoundId(roundId);
                roundScoreVO.setTeamId(teamId);
                scoreMapper.updateRoundScoreByRoundIdAndTeamId(
                        roundScoreVO.getRoundId(),
                        roundScoreVO.getTeamId(),
                        new BigDecimal(roundScoreVO.getPresentationScore()),
                        new BigDecimal(roundScoreVO.getQuestionScore()),
                        new BigDecimal(roundScoreVO.getReportScore()),
                        new BigDecimal(roundScoreVO.getTotalScore()));
                if(flag<0) {
                    return false;
                }
            }
        }
        return true;
    }


    public List<Map> listFileUploadStatusByKlassSeminarId(BigInteger klassSeminarId) throws org.apache.ibatis.javassist.NotFoundException, NotFoundException {
        List<Attendance>  attendanceList=presentationDao.listAttendanceByKlassSeminarId(klassSeminarId);
        System.out.println(attendanceList);
        List<Map> map=new ArrayList<>();
        for(Attendance item:attendanceList){
            Team team=teamDao.getTeamInfoByTeamId(item.getTeamId());
            if(team==null)
            {
                return null;
            }
            Score score=scoreDao.getSeminarScoreByKlassSeminarIdAndTeamId(item.getKlassSeminarId(),team.getTeamId());
            System.out.println(team);
            Map<String,Object> oneMap=new HashMap<>(16);
            oneMap.put("attendanceId",item.getAttendanceId());
            oneMap.put("teamId",item.getTeamId());
            oneMap.put("teamSerial",team.getTeamSerial());
            oneMap.put("klassSerial",team.getKlassSerial());
            oneMap.put("pptName",item.getPptName());
            oneMap.put("pptUrl",item.getPptUrl());
            oneMap.put("reportName",item.getReportName());
            oneMap.put("reportUrl",item.getReportUrl());
            oneMap.put("reportScore",score.getReportScore());
            map.add(oneMap);
        }
        return map;
    }


    public List<Map> listStudentKlassSeminarByKlassSeminarId(HttpServletRequest request,BigInteger klassSeminarId) throws NotFoundException, org.apache.ibatis.javassist.NotFoundException {
        BigInteger id=jwtTokenUtil.getIDFromRequest(request);
        System.out.println("id为"+id);
        List<BigInteger> teamIdList=teamDao.listTeamIdByStudentId(id);
        System.out.println("teamIdList "+teamIdList);
        SeminarVO seminarVO=seminarDao.getKlassSeminarByKlassSeminarId(klassSeminarId);
        Seminar seminar=seminarDao.getSeminarBySeminarId(seminarVO.getSeminarId());
        BigInteger courseId=courseService.getCourseIdByRoundId(seminar.getRoundId());
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
            BigInteger courseIdItem=teamDao.getCourseIdByTeamId(teamIdItem);
            if(mainCourseId.equals(courseIdItem)) {
                teamId=teamIdItem;break;
            }
        }
        System.out.println("teamId "+teamId);
        List<Attendance> attendanceList=presentationDao.listAttendanceByKlassSeminarId(klassSeminarId);
        System.out.println(attendanceList);
        BigInteger klassId=seminarDao.getKlassIdByKlassSeminarId(klassSeminarId);
        Klass klass=klassDao.getKlassByKlassId(klassId);
        int klassSerial=klass.getKlassSerial();
        List<Map> map=new ArrayList<>();
        BigInteger myAttendanceId=new BigInteger("0");
        boolean myTeamAttendance=false;
        int account=0;
        for(int i=0;i<attendanceList.size();){
            Attendance item=attendanceList.get(i);
            if(item.getTeamId().equals(teamId)){
                myTeamAttendance=true;
                myAttendanceId=item.getAttendanceId();

            }
            System.out.println(item);
            Map<String,Object> oneMap=new HashMap<>(16);
            account++;
            System.out.println(account);
            if(item.getTeamOrder()>account){
                oneMap.put("attendanceStatus",false);
                map.add(oneMap);
                continue;
            }
            else {
                oneMap.put("attendanceStatus",true);
            }
            i++;
            BigInteger teamID=item.getTeamId();
            Team team=teamDao.getTeamInfoByTeamId(teamID);
            if(team==null)
            {
                oneMap.put("attendanceId",0);
                oneMap.put("klassSerial",0);
                oneMap.put("teamSerial",0);
                oneMap.put("teamOrder",0);
            }
            oneMap.put("attendanceId",item.getAttendanceId());
            oneMap.put("klassSerial",klassSerial);
            oneMap.put("teamSerial",team.getTeamSerial());
            oneMap.put("teamOrder",item.getTeamOrder());
            if(item.getPptName()==null||item.getPptName().length()<=0){
                oneMap.put("pptStatus",false);
            }
            else {
                oneMap.put("pptStatus", true);
                oneMap.put("pptName",item.getPptName());
                oneMap.put("pptUrl",item.getPptUrl());
            }
            map.add(oneMap);
        }
        while(account<seminar.getMaxTeam()){
            Map<String,Object> oneMap=new HashMap<>(16);
            oneMap.put("attendanceStatus",false);
            map.add(oneMap);
            account++;
        }
        Map<String,Object> oneMap=new HashMap<>(16);
        oneMap.put("myTeamAttendance",myTeamAttendance);
        oneMap.put("myAttendanceId",myAttendanceId);
        map.add(oneMap);
        return map;
    }

    public List<Seminar> listSeminarByRoundId(BigInteger roundId) throws NotFoundException {
        return roundDao.listSeminarByRoundId(roundId);
    }


    public SeminarVO getKlassSeminarByKlassSeminarId(BigInteger klassSeminarId) throws NotFoundException {
        return seminarDao.getKlassSeminarByKlassSeminarId(klassSeminarId);
    }

    public BigInteger getKlassIdByKlassSeminarId(BigInteger klassSeminarId) throws NotFoundException {
        return seminarDao.getKlassIdByKlassSeminarId(klassSeminarId);
    }


    public BigInteger getRoundIdBySeminarId(BigInteger seminarId) throws NotFoundException {
        return seminarDao.getRoundIdBySeminarId(seminarId);
    }

    public List<BigInteger> listSeminarIdByRoundId(BigInteger roundId) throws NotFoundException {
        return seminarDao.listSeminarIdByRoundId(roundId);
    }

    public List<BigInteger>listKlassIdBySeminarId(BigInteger seminarId){
        return seminarDao.listKlassIdBySeminarId(seminarId);
    }

    public BigInteger getKlassSeminarIdBySeminarIdAndKlassId(BigInteger seminarId,BigInteger klassId)throws NotFoundException{
        return seminarDao.getKlassSeminarIdBySeminarIdAndKlassId(seminarId,klassId);
    }
}
