package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.QuestionListVO;
import xmu.ghct.crm.dao.*;
import xmu.ghct.crm.VO.QuestionVO;
import xmu.ghct.crm.entity.Question;
import xmu.ghct.crm.entity.Score;

import java.util.ArrayList;
import java.util.List;
import java.math.BigInteger;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Map;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    SeminarDao seminarDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    ScoreCalculationDao scoreCalculationDao;
    @Autowired
    TotalScoreDao totalScoreDao;

    /**
     * 教师提问界面右边显示所有提问学生
     * @param klassSeminarId
     * @param attendanceId
     * @return
     */
    public List<QuestionListVO> getAllQuestion(BigInteger klassSeminarId, BigInteger attendanceId)
    {
        List<QuestionListVO> questionListVOList=new ArrayList<>();
        List<Question> questionList=questionDao.listQuestionByKlassSeminarIdAndAttendanceId(klassSeminarId,attendanceId);
        for(Question item:questionList)
        {
            QuestionListVO questionListVO=new QuestionListVO();
            questionListVO.setQuestionId(item.getQuestionId());
            BigInteger klassId=questionDao.getKlassIdByKlassSeminarId(item.getKlassSeminarId());
            questionListVO.setKlassSerial(questionDao.getKlassSerialByKlassId(klassId));
            BigInteger teamId=questionDao.getTeamIdByQuestionId(item.getQuestionId());
            questionListVO.setTeamSerial(questionDao.getTeamSerialByTeamId(teamId));
            questionListVO.setStudentName(questionDao.getStudentNameByStudentId(item.getStudentId()));
            questionListVO.setSelected(item.getSelected());
            questionListVOList.add(questionListVO);
        }
        return questionListVOList;
    }

    /**
     * 教师点击下个提问时，修改当前提问为已抽到
     * @param questionId
     * @return
     */
    public boolean updateQuestionSelected(BigInteger questionId)
    {
        return questionDao.updateQuestionSelected(questionId);
    }

    /**
     * 学生提问界面，被抽取到提问，展示提问人信息
     * @param questionId
     * @return
     */
    public QuestionListVO getOneQuestion(BigInteger questionId)
    {
        QuestionListVO questionListVO=new QuestionListVO();
        Question question=questionDao.getQuestionByQuestionId(questionId);
        questionListVO.setQuestionId(questionId);
        BigInteger teamId=questionDao.getTeamIdByQuestionId(questionId);
        questionListVO.setTeamSerial(questionDao.getTeamSerialByTeamId(teamId));

        BigInteger klassId=questionDao.getKlassIdByKlassSeminarId(question.getKlassSeminarId());
        questionListVO.setKlassSerial(questionDao.getKlassSerialByKlassId(klassId));
        questionListVO.setStudentName(questionDao.getStudentNameByStudentId(question.getStudentId()));

        questionListVO.setSelected(question.getSelected());
        return questionListVO;
    }

    /**
     * 发布提问
     * @param studentId
     * @param klassSeminarId
     * @param attendanceId
     * @return
     */
    public boolean postQuestion(BigInteger studentId,BigInteger klassSeminarId,BigInteger attendanceId)
    {
        Question question=new Question();

        question.setKlassSeminarId(klassSeminarId);
        question.setAttendanceId(attendanceId);

        question.setTeamId(questionDao.getTeamIdByStudentId(studentId));
        question.setStudentId(studentId);

        question.setSelected(0);
        question.setQuestionScore((double) 0);
        return questionDao.postQuestion(question);
    }

    /**
     * 统计已提问学生数
     * @param klassSeminarId
     * @param attendanceId
     * @return
     */
    public int countQuestionNumber(BigInteger klassSeminarId,BigInteger attendanceId)
    {
        return questionDao.countQuestionNumber(klassSeminarId,attendanceId);
    }


    /**
     * 给提问打分
     * @param questionId
     * @param questionScore
     * @return
     */
    public boolean updateQuestionScore(BigInteger questionId,BigInteger klassSeminarId,Double questionScore)
    {

        BigInteger teamId=questionDao.getTeamIdByQuestionId(questionId);
        Score seminarScore=questionDao.getSeminarScoreByKlassSeminarIdAndTeamId(klassSeminarId,teamId);
        BigInteger klassId=seminarDao.getKlassIdByKlassSeminarId(klassSeminarId);
        BigInteger courseId=courseDao.getCourseIdByKlassId(klassId);
        if(seminarScore.getQuestionScore()<questionScore)
        {
            questionDao.updateSeminarScore(seminarScore.getKlassSeminarId(),teamId,questionScore);
        }
        seminarScore=questionDao.getSeminarScoreByKlassSeminarIdAndTeamId(klassSeminarId,teamId);
        Double totalScore=totalScoreDao.totalScoreCalculation(seminarScore,courseId).getTotalScore();
        boolean success=questionDao.updateSeminarScoreEnd(seminarScore.getKlassSeminarId(),teamId,totalScore);
        seminarScore=questionDao.getSeminarScoreByKlassSeminarIdAndTeamId(klassSeminarId,teamId);//更新seminar_score表

        boolean success1=questionDao.updateQuestionScoreByQuestionId(questionId,questionScore);//更新question表


        //更新round_score表
        BigInteger seminarId=questionDao.getSeminarIdByKlassSeminarId(klassSeminarId);
        BigInteger roundId=questionDao.getRoundIdBySeminarId(seminarId);

        Score roundScore=scoreCalculationDao.roundScoreCalculation(seminarScore,roundId,teamId,courseId);

        Double totalScore1=roundScore.getTotalScore();
        Double questionScore1=roundScore.getQuestionScore();
        Double presentationScore1=roundScore.getPresentationScore();
        Double reportScore1=roundScore.getReportScore();
        boolean success2=questionDao.updateRoundScoreEnd(roundId,teamId,presentationScore1,questionScore1,reportScore1,totalScore1);

        if(success==true&&success1==true&&success2==true)
            return true;
        else
            return false;
    }





}