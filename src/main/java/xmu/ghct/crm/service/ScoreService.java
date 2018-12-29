package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.ScoreVO;
import xmu.ghct.crm.VO.SeminarScoreVO;
import xmu.ghct.crm.VO.SeminarSimpleVO;
import xmu.ghct.crm.dao.*;
import xmu.ghct.crm.entity.Round;
import xmu.ghct.crm.entity.Score;
import xmu.ghct.crm.entity.Team;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScoreService {

    @Autowired
    ScoreDao scoreDao;
    @Autowired
    RoundDao roundDao;

    @Autowired
    TeamDao teamDao;

    @Autowired
    KlassDao klassDao;

    @Autowired
    CourseDao courseDao;

    @Autowired
    SeminarDao seminarDao;
    /**
     * @cyq
     * 教师查所有小组成绩：根据课程id查询所有小组的所有成绩
     * @param courseId
     * @return
     */
   /* public List<ScoreVO> listAllScoreByCourseId(BigInteger courseId){
        return scoreDao.listScoreByCourseId(courseId);
    }
    */

    /**
     * @cyq
     * 学生查自己小组成绩：根据课程id和小组id查到该组所有的成绩
     * @return
     */

    /*
    public List<ScoreVO> listTeamScoreByCourseId(BigInteger courseId,BigInteger teamId){
        return scoreDao.listTeamScoreByCourseId(courseId,teamId);
    }*/

    public int deleteSeminarScoreBySeminarId(BigInteger seminarId){
        return scoreDao.deleteSeminarScoreBySeminarId(seminarId);
    }

//   public int updateRoundScoreByRoundIdAndTeamId(ScoreVO scoreVO){
//        return scoreDao.updateRoundScoreByRoundIdAndTeamId(scoreVO);
//    }


    public Score getKlassSeminarScoreByKlassSeminarIdAndTeamId(BigInteger klassSeminarId,BigInteger teamId){
        return scoreDao.getSeminarScoreByKlassSeminarIdAndTeamId(klassSeminarId,teamId);
    }

    /**
     * @author hzm
     *根据roundId获得轮次下所有小组的轮次成绩
     * @param roundId
     * @return
     */
    public List<ScoreVO> listRoundScoreByRoundId(BigInteger roundId){
        List<ScoreVO> scoreVOList= scoreDao.listRoundScoreByRoundId(roundId);
        for(ScoreVO item:scoreVOList){
           int teamSerial=teamDao.getTeamSerialByTeamId(item.getTeamId());
           item.setTeamSerial(teamSerial);
           BigInteger klassId=teamDao.getKlassIdByTeamId(item.getTeamId());
           int klassSerial=klassDao.getKlassSerialByKlassId(klassId);
           item.setKlassSerial(klassSerial);
        }
        return scoreVOList;
    }

    public List<Map<String,Object>> listTeamRoundInfoByCourseIdAndTeamId(BigInteger courseId,BigInteger teamId){
        List<Round> roundList=roundDao.listRoundByCourseId(courseId);
        List<ScoreVO> scoreVOList=new ArrayList<>();
        List<Map<String,Object>> map=new ArrayList<>();
        for(Round item:roundList){
            Map<String,Object> oneMap=new HashMap<>();
            ScoreVO scoreVO=scoreDao.getTeamRoundScoreByRoundIdAndTeamId(item.getRoundId(),teamId);
            oneMap.put("roundId",item.getRoundId());
            oneMap.put("roundTotalScore",scoreVO.getTotalScore());
            oneMap.put("roundSerial",item.getRoundSerial());
            map.add(oneMap);
        }
        return map;

    }

    /**
     * 返回轮次下的讨论课简单信息（id，名字，要求）
     * @param roundId
     * @return
     */
    public List<SeminarSimpleVO> getSeminarByRoundId(BigInteger roundId,BigInteger teamId){
        List<SeminarSimpleVO> seminarSimpleVOS= roundDao.getSeminarByRoundId(roundId);
        BigInteger klassId=teamDao.getKlassIdByTeamId(teamId);
        List<SeminarSimpleVO> seminarScoreVOList=new ArrayList<>();
        for(SeminarSimpleVO item:seminarSimpleVOS){
            BigInteger klassSeminarId=seminarDao.getKlassSeminarIdBySeminarIdAndKlassId(item.getId(),klassId);
            Score score=scoreDao.getSeminarScoreByKlassSeminarIdAndTeamId(klassSeminarId,teamId);
            if(score!=null){
                seminarScoreVOList.add(item);
            }
        }
        return seminarScoreVOList;
    }

    /**
     * 获取某队伍的某次讨论课成绩
     * @param seminarId
     * @param teamId
     * @return
     */
    public Score getTeamSeminarScoreBySeminarIdAndTeamId(BigInteger seminarId,BigInteger teamId){
        BigInteger klassId=teamDao.getKlassIdByTeamId(teamId);
        BigInteger klassSeminarId=seminarDao.getKlassSeminarIdBySeminarIdAndKlassId(seminarId,klassId);
        Score score=scoreDao.getSeminarScoreByKlassSeminarIdAndTeamId(klassSeminarId,teamId);
        return score;
    }
}
