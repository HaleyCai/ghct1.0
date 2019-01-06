package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xmu.ghct.crm.entity.Question;
import xmu.ghct.crm.entity.Score;

import java.math.BigInteger;
import java.util.List;

/**
 * @author gfj
 */
@Mapper
@Repository
public interface QuestionMapper {


    /**
     * 获取提问的队伍号
     * @param questionId
     * @return
     */
    BigInteger getTeamIdByQuestionId(BigInteger questionId);

    /**
     * 根据学生id获取学生名字
     * @param studentId
     * @return
     */
    String getStudentNameByStudentId(BigInteger studentId);

    /**
     * 更新提问选择状态
     * @param questionId
     * @return
     */
    boolean updateQuestionSelected(BigInteger questionId);

    /**
     * 随机抽取一次展示中的一个提问
     * @param attendanceId
     * @return
     */
    Question getOneQuestion(BigInteger attendanceId);

    /**
     * 学生提问
     * @param question
     * @return
     */
    int postQuestion(Question question);

    /**
     * 获取该队伍该轮次的分数
     * @param klassSeminarId
     * @param teamId
     * @return
     */
    Score getSeminarScoreByKlassSeminarIdAndTeamId(BigInteger klassSeminarId, BigInteger teamId);

    /**
     * 更新提问成绩
     * @param klassSeminarId
     * @param teamId
     * @param questionScore
     * @return
     */
    Boolean updateSeminarScore(@Param("klassSeminarId") BigInteger klassSeminarId,
                               @Param("teamId") BigInteger teamId,
                               @Param("questionScore") Double questionScore);

    /**
     * 跟新提问总成绩
     * @param klassSeminarId
     * @param teamId
     * @param totalScore
     * @return
     */
    Boolean updateSeminarScoreEnd(BigInteger klassSeminarId,BigInteger teamId,Double totalScore);

    /**
     * 获取讨论课id
     * @param klassSeminarId
     * @return
     */
    BigInteger getSeminarIdByKlassSeminarId(BigInteger klassSeminarId);

    /**
     * 更新轮次成绩
     * @param roundId
     * @param teamId
     * @param presentationScore1
     * @param questionScore1
     * @param reportScore1
     * @param totalScore1
     * @return
     */
    Boolean updateRoundScoreEnd(@Param("roundId") BigInteger roundId,
                                @Param("teamId") BigInteger teamId,
                                @Param("presentationScore1") Double presentationScore1,
                                @Param("questionScore1") Double questionScore1,
                                @Param("reportScore1") Double reportScore1,
                                @Param("totalScore1") Double totalScore1);

    /**
     * 更新提问成绩
     * @param questionId
     * @param questionScore
     * @return
     */
    int updateQuestionScoreByQuestionId(BigInteger questionId,Double questionScore);

    /**
     * 获得队伍id
     * @param studentId
     * @return
     */
    BigInteger getTeamIdByStudentId(BigInteger studentId);

    /**
     * 获取提问列表
     * @param attendanceId
     * @return
     */
    List<Question> listQuestionByAttendanceId(BigInteger attendanceId);

    /**
     * 判断一个人是否重复提问
     */
    Question getQuestionByAttendanceIdAndStudentId(BigInteger attendanceId,BigInteger studentId);
}