package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.VO.QuestionListVO;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.websocket.GreetingController;
import xmu.ghct.crm.dao.*;
import xmu.ghct.crm.VO.QuestionVO;
import xmu.ghct.crm.entity.Question;
import xmu.ghct.crm.entity.Score;

import java.util.*;
import java.math.BigInteger;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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

    GreetingController greetingController;

    /**
     * 教师提问界面右边显示所有提问学生
     * @param seminarId
     * @param klassId
     * @param attendanceId
     * @return
     */
    public List<QuestionListVO> getAllQuestion(BigInteger seminarId, BigInteger klassId,BigInteger attendanceId)
    {
        Queue<QuestionListVO> questionQueue=greetingController.getQuestionQueue();

        List<QuestionListVO> questionListVOList=new ArrayList<>();

        for(QuestionListVO item:questionQueue)
        {

            questionListVOList.add(item);
        }
        return questionListVOList;
    }


    /**
     * 被抽取到提问，展示提问人信息
     * @param seminarId
     * @param klassId
     * @param attendanceId
     * @return
     */
    public QuestionListVO getOneQuestion(BigInteger seminarId,
                                         BigInteger klassId,
                                         BigInteger attendanceId)
    {
        QuestionListVO question=greetingController.getOneQuestion();
        questionDao.updateQuestionSelected(question.getQuestionId());

        return question;
    }



    /**
     * 发布提问
     * @param seminar
     * @param klassId
     * @param attendanceId
     * @param studentId
     * @return
     */
    public boolean postQuestion(BigInteger seminar,BigInteger klassId,
                                BigInteger attendanceId,BigInteger studentId)
    {
        Question question=new Question();
        BigInteger klassSeminarId=seminarDao.getKlassSeminarIdBySeminarIdAndKlassId(seminar,klassId);
        question.setKlassSeminarId(klassSeminarId);
        question.setAttendanceId(attendanceId);
        BigInteger teamId=questionDao.getTeamIdByStudentId(studentId);
        question.setTeamId(teamId);
        question.setStudentId(studentId);
        question.setSelected(0);
        question.setQuestionScore((double) 0);
        questionDao.postQuestion(question);
        BigInteger questionId=questionDao.getQuestionId(klassSeminarId,attendanceId,studentId);

        QuestionListVO questionListVO=new QuestionListVO();
        questionListVO.setQuestionId(questionId);
        questionListVO.setKlassSerial(questionDao.getKlassSerialByKlassId(klassId));
        questionListVO.setTeamSerial(questionDao.getTeamSerialByTeamId(teamId));
        questionListVO.setStudentId(studentId);
        questionListVO.setStudentName(questionDao.getStudentNameByStudentId(studentId));
        questionListVO.setAttendanceId(attendanceId);
        questionListVO.setSelected(0);

        if(greetingController.postQuestion(questionListVO))
            return true;
        else
            return false;
    }


    /**
     * 给提问打分
     * @param questionId
     * @param questionScore
     * @return
     */
    public boolean updateQuestionScore(BigInteger seminarId,BigInteger klassId,
                                       BigInteger questionId,Double questionScore) throws NotFoundException {

        BigInteger teamId=questionDao.getTeamIdByQuestionId(questionId);
        BigInteger klassSeminarId=seminarDao.getKlassSeminarIdBySeminarIdAndKlassId(seminarId,klassId);
        Score seminarScore=questionDao.getSeminarScoreByKlassSeminarIdAndTeamId(klassSeminarId,teamId);
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