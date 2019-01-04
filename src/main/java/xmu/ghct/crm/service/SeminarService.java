package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import xmu.ghct.crm.VO.*;
import xmu.ghct.crm.dao.*;
import xmu.ghct.crm.entity.*;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.mapper.ScoreMapper;
import xmu.ghct.crm.mapper.SeminarMapper;
import xmu.ghct.crm.security.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    ScoreCalculationDao scoreCalculationDao;

    @Autowired
    SeminarMapper seminarMapper;

    @Autowired
    TotalScoreDao totalScoreDao;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public int creatSeminar(BigInteger courseId,Map<String,Object> seminarMap) throws ParseException, NotFoundException, SQLException {
        Seminar seminar=new Seminar();
        seminar.setCourseId(courseId);
        if(seminarMap.get("roundId").toString().equals("0")){
            Round round=new Round();
            round.setCourseId(courseId);
            round.setPresentationScoreMethod(1);
            round.setQuestionScoreMethod(0);
            round.setReportScoreMethod(1);
            round.setRoundSerial(roundDao.getNewRoundNum(round.getCourseId()));//默认创建，序号为最大序号+1
            roundDao.insertRound(round,courseId);
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
        seminar.setSeminarSerial(new Integer(seminarMap.get("seminarSerial").toString()));
        Date start = dateDao.transferToDateTime(seminarMap.get("enrollStartTime").toString());
        seminar.setEnrollStartTime(start);
        Date end = dateDao.transferToDateTime(seminarMap.get("enrollEndTime").toString());
        seminar.setEnrollEndTime(end);
        int flag= seminarDao.creatSeminar(seminar);
        List<Klass> klassList=klassDao.listKlassByCourseId(courseId);
        for(Klass item:klassList){
            seminarDao.insertKlassSeminarBySeminarIdAndKlassId(seminar.getSeminarId(),item.getKlassId());
        }
        return flag;
    }


    /**
     * 获取某轮次下所有讨论课的简略信息
     * @param roundId
     * @return
     */
    public List<SeminarSimpleVO> getSeminarByRoundId(BigInteger roundId) throws NotFoundException {
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
        seminar.setSeminarSerial(new Integer(seminarMap.get("seminarSerial").toString()));
        seminar.setRoundId(new BigInteger(seminarMap.get("roundId").toString()));
        seminar.setCourseId(new BigInteger(seminarMap.get("courseId").toString()));
        seminar.setSeminarName(seminarMap.get("seminarName").toString());
        seminar.setSeminarId(seminarId);
        seminar.setVisible(new Integer(seminarMap.get("visible").toString()));
        return seminarDao.updateSeminarBySeminarId(seminar);
    }


    public int deleteSeminarBySeminarId(BigInteger seminarId) throws NotFoundException {
        return seminarDao.deleteSeminarBySeminarId(seminarId);
    }

    public int deleteKlassSeminarBySeminarId(BigInteger seminarId) throws NotFoundException {
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
     * @param seminarId
     * @param teamId
     * @param seminarScoreMap
     * @return
     */
    public int updateSeminarScoreBySeminarIdAndTeamId(BigInteger seminarId, BigInteger teamId, Map<String,Object> seminarScoreMap) throws NotFoundException {
        Score score=new Score();
        BigInteger roundId=seminarMapper.getRoundIdBySeminarId(seminarId);
        BigInteger courseId=courseDao.getCourseIdByTeamId(teamId);
        BigInteger klassId=teamDao.getKlassIdByTeamId(teamId);
        BigInteger klassSeminarId=seminarDao.getKlassSeminarIdBySeminarIdAndKlassId(seminarId,klassId);
        Course course=courseDao.getCourseByCourseId(courseId);
        double presentationScore=new Double(seminarScoreMap.get("presentationScore").toString());
        score.setTeamId(teamId);
        score.setKlassSeminarId(klassSeminarId);
        score.setPresentationScore(presentationScore);
        double questionScore=new Double(seminarScoreMap.get("questionScore").toString());
        score.setQuestionScore(questionScore);
        double reportScore=new Double(seminarScoreMap.get("reportScore").toString());
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
        scoreMapper.updateRoundScoreByRoundIdAndTeamId(roundScoreVO);
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
            teamList.add(team);
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


    public boolean updateReportScoreByKlassSeminarId(BigInteger klassSeminarId,List<Map> reportMapList) throws NotFoundException{
        for(Map<String,Object> reportMap:reportMapList)
        {
            BigInteger teamId=new BigInteger(reportMap.get("teamId").toString());
            BigInteger courseId=teamDao.getCourseIdByTeamId(teamId);
            BigInteger seminarId=seminarDao.getKlassSeminarByKlassSeminarId(klassSeminarId).getSeminarId();
            BigInteger roundId=seminarDao.getRoundIdBySeminarId(seminarId);
            Score score=scoreDao.getSeminarScoreByKlassSeminarIdAndTeamId(klassSeminarId,teamId);
            Double reportScore=new Double(reportMap.get("reportScore").toString());
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
            scoreMapper.updateRoundScoreByRoundIdAndTeamId(roundScoreVO);
            if(flag<0) return false;
        }
        return true;
    }


    public List<Map> listFileUploadStatusByKlassSeminarId(BigInteger klassSeminarId) throws org.apache.ibatis.javassist.NotFoundException, NotFoundException {
        List<Attendance>  attendanceList=presentationDao.listAttendanceByKlassSeminarId(klassSeminarId);
        System.out.println(attendanceList);
        List<Map> map=new ArrayList<>();
        for(Attendance item:attendanceList){
            Team team=teamDao.getTeamInfoByTeamId(item.getTeamId());
            Score score=scoreDao.getSeminarScoreByKlassSeminarIdAndTeamId(item.getKlassSeminarId(),team.getTeamId());
            System.out.println(team);
            Map<String,Object> oneMap=new HashMap<>();
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
        List<BigInteger> teamIdList=teamDao.listTeamIdByStudentId(id);
        SeminarVO seminarVo=seminarDao.getKlassSeminarByKlassSeminarId(klassSeminarId);
        BigInteger courseId=courseDao.getCourseIdByKlassId(seminarVo.getKlassId());
        BigInteger teamId=new BigInteger("0");
        for(BigInteger teamIdItem:teamIdList){
            BigInteger courseIdItem=teamDao.getCourseIdByTeamId(teamIdItem);
            if(courseId.equals(courseIdItem)) teamId=teamIdItem;
        }
        List<Attendance> attendanceList=presentationDao.listAttendanceByKlassSeminarId(klassSeminarId);
        System.out.println(attendanceList);
        SeminarVO seminarVO=seminarDao.getKlassSeminarByKlassSeminarId(klassSeminarId);
        Seminar seminar=seminarDao.getSeminarBySeminarId(seminarVO.getSeminarId());
        BigInteger klassId=seminarDao.getKlassIdByKlassSeminarId(klassSeminarId);
        Klass klass=klassDao.getKlassByKlassId(klassId);
        int klassSerial=klass.getKlassSerial();
        List<Map> map=new ArrayList<>();
        boolean myTeamAttendance=false;
        int account=0;
        for(int i=0;i<attendanceList.size();){
            Attendance item=attendanceList.get(i);
            if(item.getTeamId().equals(teamId)){
                myTeamAttendance=true;
            }
            System.out.println(item);
            Map<String,Object> oneMap=new HashMap<>();
            account++;
            System.out.println(account);
            if(item.getTeamOrder()>account){
                oneMap.put("attendanceStatus",false);
                map.add(oneMap);
                continue;
            }
            else oneMap.put("attendanceStatus",true);
            i++;
            BigInteger teamID=item.getTeamId();
            Team team=teamDao.getTeamInfoByTeamId(teamID);

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
            Map<String,Object> oneMap=new HashMap<>();
            oneMap.put("attendanceStatus",false);
            map.add(oneMap);
            account++;
        }
        Map<String,Object> oneMap=new HashMap<>();
        oneMap.put("myTeamAttendance",myTeamAttendance);
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
}
