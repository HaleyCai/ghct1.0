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
    /*
    public List<ScoreVO> listTeamScoreByCourseId(BigInteger courseId,BigInteger teamId){
        //1.根据courseId查所有的roundId【表round】
        //2.1.根据roundId查teamId的轮次成绩【表round_score】（总成绩）+每节seminar的成绩=》
        //2.2.根据roundId查其下所有的seminarId【表seminar】
        //2.3.根据teamId查小组在哪个班klassId【表team】
        //2.4.根据klassId和seminarId查所有的klassSeminarId【表klass_seminar】
        //2.5.根据klassSeminarId查teamId的每节seminar成绩【表seminar_score】
    }*/

    public int deleteSeminarScoreBySeminarId(BigInteger seminarId){
        return scoreDao.deleteSeminarScoreBySeminarId(seminarId);
    }

    public int updateRoundScoreByRoundIdAndTeamId(ScoreVO scoreVO){
        return scoreDao.updateRoundScoreByRoundIdAndTeamId(scoreVO);
    }
}
