package xmu.ghct.crm.service;

import com.fasterxml.jackson.databind.node.BigIntegerNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import xmu.ghct.crm.VO.*;
import xmu.ghct.crm.dao.*;
import xmu.ghct.crm.entity.*;
import xmu.ghct.crm.mapper.ScoreMapper;
import xmu.ghct.crm.mapper.SeminarMapper;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SeminarService {
    @Autowired
    SeminarDao seminarDao;

    @Autowired
    RoundDao roundDao;

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

    public int creatSeminar(Map<String,Object> seminarMap) throws ParseException {
        Seminar seminar=new Seminar();
        seminar.setCourseId(new BigInteger(seminarMap.get("courseId").toString()));
        if(seminarMap.get("roundId").toString()==""){
            Round round=new Round();
            round.setCourseId(new BigInteger(seminarMap.get("courseId").toString()));
            round.setPresentationScoreMethod(1);
            round.setQuestionScoreMethod(0);
            round.setReportScoreMethod(1);
            round.setRoundSerial(roundDao.getNewRoundNum(round.getCourseId()));//默认创建，序号为最大序号+1
            roundDao.insertRound(round);
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
        return seminarDao.creatSeminar(seminar);
    }


    /**
     * 获取某轮次下所有讨论课的简略信息
     * @param roundId
     * @return
     */
    public List<SeminarSimpleVO> getSeminarByRoundId(BigInteger roundId)
    {
        List<SeminarSimpleVO> list = roundDao.getSeminarByRoundId(roundId);
        if(list==null)
        {
            //throw
        }
        return list;
    }


    /**
     * 根据seminarId修改讨论课信息
     * @param seminarId
     * @return
     */
    public int updateSeminarBySeminarId(BigInteger seminarId,@RequestBody Map<String,Object> seminarMap) throws ParseException {
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


    public int deleteSeminarBySeminarId(BigInteger seminarId){
        return seminarDao.deleteSeminarBySeminarId(seminarId);
    }

    public int deleteKlassSeminarBySeminarId(BigInteger seminarId){
        return seminarDao.deleteKlassSeminarBySeminarId(seminarId);
    }

    public  Seminar getSeminarBySeminarId(BigInteger seminarId){
        return seminarDao.getSeminarBySeminarId(seminarId);
    }

    public int updateKlassSeminarBySeminarIdAndKlassId(BigInteger klassId,BigInteger seminarId,Map<String,Object> seminarMap) throws ParseException {
        Date reportDDL = dateDao.transferToDateTime(seminarMap.get("reportDDL").toString());
        return seminarDao.updateKlassSeminarBySeminarIdAndKlassId(klassId,seminarId,reportDDL);
    }

    public int deleteKlassSeminarBySeminarIdAndKlassId(BigInteger klassId,BigInteger seminarId){
        return seminarDao.deleteKlassSeminarBySeminarIdAndKlassId(klassId,seminarId);
    }

    /**
     * @author hzm
     * 根据seminarId和klassId获得班级讨论课信息
     * @param klassId
     * @param seminarId
     * @return
     */
    public SeminarVO getKlassSeminarByKlassIdAndSeminarId(BigInteger klassId, BigInteger seminarId) {
        SeminarVO seminarVO=seminarDao.getKlassSeminarByKlassIdAndSeminarId(klassId,seminarId);
        Seminar seminar=seminarDao.getSeminarBySeminarId(seminarId);
        seminarVO.setEnrollEndTime(seminar.getEnrollEndTime());
        seminarVO.setEnrollStartTime(seminar.getEnrollStartTime());
        seminarVO.setMaxTeam(seminar.getMaxTeam());
        seminarVO.setIntroduction(seminar.getIntroduction());
        seminarVO.setSeminarName(seminar.getSeminarName());
        seminarVO.setSeminarId(seminarId);
        seminarVO.setSeminarSerial(seminar.getSeminarSerial());
        return seminarVO;
    }


    /**
     * @author hzm
     * 修改班级讨论课状态
     * @param seminarId
     * @param klassMap
     * @return
     */
    public int updateKlassSeminarStatus(BigInteger seminarId,Map<String,Object> klassMap){
        SeminarVO seminarVO=new SeminarVO();
        seminarVO.setSeminarId(seminarId);
        seminarVO.setKlassId(new BigInteger(klassMap.get("klassId").toString()));
        seminarVO.setStatus(new Integer(klassMap.get("status").toString()));
        return seminarDao.updateKlassSeminarStatus(seminarVO);
    }

    public SeminarScoreVO getTeamSeminarScoreByTeamIdAndSeminarId(BigInteger teamId, BigInteger seminarId){
        SeminarScoreVO seminarScoreVO=new SeminarScoreVO();
        Team teamInfo=teamDao.getTeamInfoByTeamId(teamId);
        Seminar seminarInfo=seminarDao.getSeminarBySeminarId(seminarId);
        Klass klassInfo=klassDao.getKlassByKlassId(teamInfo.getKlassId());
        Score seminarScore=scoreMapper.getSeminarScoreBySeminarIdAndTeamId(seminarId,teamId);
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
    public int updateSeminarScoreBySeminarIdAndTeamId(BigInteger seminarId, BigInteger teamId, Map<String,Object> seminarScoreMap){
        Score score=new Score();
        BigInteger roundId=seminarMapper.getRoundIdBySeminarId(seminarId);
        BigInteger courseId=courseDao.getCourseIdByTeamId(teamId);
        CourseVO courseVO=courseDao.getCourseByCourseId(courseId);
        double presentationScore=new Double(seminarScoreMap.get("presentationScore").toString());
        score.setTeamId(teamId);
        score.setKlassSeminarId(seminarId);
        score.setPresentationScore(presentationScore);
        double questionScore=new Double(seminarScoreMap.get("questionScore").toString());
        score.setQuestionScore(questionScore);
        double reportScore=new Double(seminarScoreMap.get("reportScore").toString());
        score.setReportScore(reportScore);
        double totalScore=questionScore*(courseVO.getPresentationPercentage()*0.01)+questionScore*(courseVO.getQuestionPercentage()*0.01)+
                          reportScore*(courseVO.getReportPercentage()*0.01);
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
     * @author hzm
     * 根据klassId 和seminarId获得班级讨论课所有队伍成绩
     * @param klassId
     * @param seminarId
     * @return
     */
    public List<SeminarScoreVO> listKlassSeminarScoreByKlassIdAndSeminarId(BigInteger klassId,BigInteger seminarId){
        List<Team> teamList=teamDao.listTeamByKlassId(klassId);
        Klass klass=klassDao.getKlassByKlassId(klassId);
        BigInteger klassSeminarId=seminarDao.getKlassSeminarIdBySeminarIdAndKlassId(seminarId,klassId);
        Seminar seminar=seminarDao.getSeminarBySeminarId(seminarId);
        List<SeminarScoreVO> seminarScoreVOList=new ArrayList<>();
        for(Team item:teamList){
            SeminarScoreVO seminarScoreVO=new SeminarScoreVO();
            seminarScoreVO.setTeamId(item.getTeamId());
            seminarScoreVO.setKlassSerial(klass.getKlassSerial());
            seminarScoreVO.setSeminarName(seminar.getSeminarName());
            seminarScoreVO.setTeamSerial(item.getTeamSerial());
            Score score=scoreMapper.getSeminarScoreBySeminarIdAndTeamId(klassSeminarId,item.getTeamId());
            seminarScoreVO.setPresentationScore(score.getPresentationScore());
            seminarScoreVO.setQuestionScore(score.getQuestionScore());
            seminarScoreVO.setReportScore(score.getReportScore());
            seminarScoreVO.setTotalScore(score.getTotalScore());
            seminarScoreVOList.add(seminarScoreVO);
        }
        return seminarScoreVOList;
    }


    public SeminarVO getKlassSeminarByKlassSeminarId(BigInteger klassSeminarId){
        return seminarDao.getKlassSeminarByKlassSeminarId(klassSeminarId);
    }
}
