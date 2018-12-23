package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Course;
import xmu.ghct.crm.entity.Score;
import xmu.ghct.crm.mapper.CourseMapper;

import java.math.BigInteger;

@Component
public class TotalScoreDao {

    @Autowired
    CourseMapper courseMapper;

    /**
     * @author hzm
     * 计算seminar总成绩
     * @param score
     * @param courseId
     * @return
     */
    public Score totalScoreCalculation(Score score, BigInteger courseId){
        Course course=courseMapper.getCourseByCourseId(courseId);
        double totalScore=score.getPresentationScore()*(course.getPresentationPercentage()*0.01)+score.getQuestionScore()*(course.getQuestionPercentage()*0.01)+
                score.getReportScore()*(course.getReportPercentage()*0.01);
        score.setTotalScore(totalScore);
        return  score;
    }
}
