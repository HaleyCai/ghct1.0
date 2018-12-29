package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.ScoreVO;
import xmu.ghct.crm.VO.SeminarScoreVO;
import xmu.ghct.crm.dao.RoundDao;
import xmu.ghct.crm.dao.ScoreDao;
import xmu.ghct.crm.entity.Round;
import xmu.ghct.crm.entity.Score;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Service
public class ScoreService {

    @Autowired
    ScoreDao scoreDao;
    @Autowired
    RoundDao roundDao;
    /**
     * @cyq
     * 教师查所有小组成绩：根据课程id查询所有小组的所有成绩
     * @param courseId
     * @return
     */
    public List<ScoreVO> listAllScoreByCourseId(BigInteger courseId){
        return scoreDao.listScoreByCourseId(courseId);
    }

    /**
     * @cyq
     * 学生查自己小组成绩：根据课程id和小组id查到该组所有的成绩
     * @return
     */

    public List<ScoreVO> listTeamScoreByCourseId(BigInteger courseId,BigInteger teamId){
        return scoreDao.listTeamScoreByCourseId(courseId,teamId);
    }

    public int deleteSeminarScoreBySeminarId(BigInteger seminarId){
        return scoreDao.deleteSeminarScoreBySeminarId(seminarId);
    }

    public int updateRoundScoreByRoundIdAndTeamId(ScoreVO scoreVO){
        return scoreDao.updateRoundScoreByRoundIdAndTeamId(scoreVO);
    }


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
       return scoreDao.listRoundScoreByRoundId(roundId);
    }
}
