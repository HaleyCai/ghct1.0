package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.vo.ScoreVO;
import xmu.ghct.crm.vo.SeminarSimpleVO;
import xmu.ghct.crm.vo.SeminarVO;
import xmu.ghct.crm.dao.*;
import xmu.ghct.crm.entity.Round;
import xmu.ghct.crm.entity.Score;
import xmu.ghct.crm.entity.Seminar;
import xmu.ghct.crm.exception.NotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hzm
 */
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

    @Autowired
    SeminarService seminarService;

    @Autowired
    ScoreService scoreService;

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

    public int deleteSeminarScoreBySeminarId(BigInteger seminarId) {
        return scoreDao.deleteSeminarScoreBySeminarId(seminarId);
    }

//   public int updateRoundScoreByRoundIdAndTeamId(ScoreVO scoreVO){
//        return scoreDao.updateRoundScoreByRoundIdAndTeamId(scoreVO);
//    }


    public Score getKlassSeminarScoreByKlassSeminarIdAndTeamId(BigInteger klassSeminarId,BigInteger teamId) throws NotFoundException{
        return scoreDao.getSeminarScoreByKlassSeminarIdAndTeamId(klassSeminarId,teamId);
    }

    /**
     * @author hzm
     *根据roundId获得轮次下所有小组的轮次成绩
     * @param roundId
     * @return
     */
    public List<ScoreVO> listRoundScoreByRoundId(BigInteger roundId) throws NotFoundException{
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

    public List<Map<String,Object>> listTeamRoundInfoByCourseIdAndTeamId(BigInteger courseId,BigInteger teamId) throws NotFoundException
    {
        List<Round> roundList=roundDao.listRoundByCourseId(courseId);
        List<ScoreVO> scoreVOList=new ArrayList<>();
        List<Map<String,Object>> map=new ArrayList<>();
        for(Round item:roundList){
            Map<String,Object> oneMap=new HashMap<>(16);
            ScoreVO scoreVO=scoreDao.getTeamRoundScoreByRoundIdAndTeamId(item.getRoundId(),teamId);
            oneMap.put("roundId",item.getRoundId());
            oneMap.put("roundTotalScore",scoreVO.getTotalScore());
            oneMap.put("roundSerial",item.getRoundSerial());
            map.add(oneMap);
        }
        return map;

    }


    public List<Score> listKlassSeminarScoreByRoundIdAndTeamId(BigInteger roundId,BigInteger teamId)throws NotFoundException{
        List<BigInteger> seminarIdList=seminarDao.listSeminarIdByRoundId(roundId);
        //队伍所属班级ID
        List<BigInteger> klassIdS=teamDao.listKlassIdByTeamId(teamId);
        System.out.println("&&"+klassIdS);
        List<SeminarVO> klassSeminarList=new ArrayList<>();
        List<Score> scoreList=new ArrayList<>();
        for(BigInteger item:seminarIdList){
            System.out.println(item);
            //讨论课所属班级ID
            List<BigInteger> klassId=seminarDao.listKlassIdBySeminarId(item);
            System.out.println("%%"+klassId);
            for(BigInteger klass1:klassIdS){
                for(BigInteger klass2:klassId){
                    if(klass1.equals(klass2)) {
                        System.out.println(klass1+"***************"+klass2);
                        SeminarVO seminarVO=seminarService.getKlassSeminarByKlassIdAndSeminarId(klass1,item);
                        klassSeminarList.add(seminarVO);
                    }
                }
            }
        }

        for(SeminarVO item:klassSeminarList){
            Score score=scoreDao.getSeminarScoreByKlassSeminarIdAndTeamId(item.getKlassSeminarId(),teamId);
            Seminar seminar=seminarService.getSeminarBySeminarId(item.getSeminarId());
            if(score!=null){
                score.setSeminarName(seminar.getSeminarName());
                scoreList.add(score);
            }
        }
        return scoreList;
    }

    /**
     * 返回轮次下的讨论课成绩
     * @param roundId
     * @return
     */
    public List<SeminarSimpleVO> getSeminarByRoundId(BigInteger roundId,BigInteger teamId) throws NotFoundException
    {
        System.out.println("roundId "+roundId);
        System.out.println("teamId "+teamId);
        List<SeminarSimpleVO> seminarSimpleVOS= roundDao.getSeminarByRoundId(roundId);

        if(seminarSimpleVOS.isEmpty())
        {
            return null;
        }
        System.out.println("seminarSimpleVOS "+seminarSimpleVOS);
        List<BigInteger> klassIdList=teamDao.listKlassIdByTeamId(teamId);
        System.out.println("klassIdList "+klassIdList);
        List<SeminarSimpleVO> seminarScoreVOList=new ArrayList<>();
        List<BigInteger> klassIdS=seminarDao.listKlassIdBySeminarId(seminarSimpleVOS.get(0).getId());
        System.out.println("klassIds "+klassIdS);

        BigInteger klassId=new BigInteger("0");
        for(BigInteger klass1:klassIdList){
            for(BigInteger klass2:klassIdS){
                if(klass1.equals(klass2)) {
                    klassId=klass1;
                }
            }
        }
        System.out.println("klassId "+klassId);
        for(SeminarSimpleVO item:seminarSimpleVOS){
            BigInteger klassSeminarId=seminarDao.getKlassSeminarIdBySeminarIdAndKlassId(item.getId(),klassId);
            Score score=scoreDao.getSeminarScoreByKlassSeminarIdAndTeamId(klassSeminarId,teamId);
            item.setKlassSeminarId(klassSeminarId);
            if(score!=null){
                seminarScoreVOList.add(item);
            }
        }
        return seminarScoreVOList;
    }

    /**
     * 获取某队伍的某次讨论课成绩
     * @param klassSeminarId
     * @param teamId
     * @return
     */
    public Score getTeamSeminarScoreByKlassSeminarIdAndTeamId(BigInteger klassSeminarId,
                                                              BigInteger teamId) throws NotFoundException
    {
        Score score=scoreDao.getSeminarScoreByKlassSeminarIdAndTeamId(klassSeminarId,teamId);
        return score;
    }
}
