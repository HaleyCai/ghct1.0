package xmu.ghct.crm.service;

import org.codehaus.jackson.map.JsonSerializer;
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
import xmu.ghct.crm.exception.NotFoundException;

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
    PresentationDao presentationDao;
    @Autowired
    KlassDao klassDao;
    @Autowired
    ScoreCalculationDao scoreCalculationDao;
    @Autowired
    TeamDao teamDao;
    @Autowired
    TotalScoreDao totalScoreDao;

    /**
     * 教师提问界面右边显示所有提问学生
     * @param attendanceId
     * @return
     */
    public List<QuestionListVO> getAllQuestion(BigInteger attendanceId) throws NotFoundException {
        List<QuestionListVO> questionListVOList=new ArrayList<>();
        List<Question> questionList=questionDao.listQuestionByAttendanceId(attendanceId);
        for(Question item:questionList)
        {
            QuestionListVO questionListVO=new QuestionListVO();
            questionListVO.setQuestionId(item.getQuestionId());
            BigInteger klassId=seminarDao.getKlassIdByKlassSeminarId(item.getKlassSeminarId());
            questionListVO.setKlassSerial(klassDao.getKlassSerialByKlassId(klassId));
            BigInteger teamId=questionDao.getTeamIdByQuestionId(item.getQuestionId());
            questionListVO.setTeamSerial(teamDao.getTeamSerialByTeamId(teamId));
            questionListVO.setStudentName(questionDao.getStudentNameByStudentId(item.getStudentId()));
            questionListVO.setSelected(item.getSelected());
            questionListVO.setQuestionScore(item.getQuestionScore());
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
     * @param attendanceId
     * @return
     */
    public QuestionListVO getOneQuestion(BigInteger attendanceId) throws NotFoundException {
        QuestionListVO questionListVO=new QuestionListVO();
        Question question=questionDao.getOneQuestion(attendanceId);
        if(question!=null) {
            questionListVO.setQuestionId(question.getQuestionId());

            BigInteger klassId = seminarDao.getKlassIdByKlassSeminarId(question.getKlassSeminarId());
            questionListVO.setKlassSerial(klassDao.getKlassSerialByKlassId(klassId));

            questionListVO.setTeamSerial(teamDao.getTeamSerialByTeamId(question.getTeamId()));

            questionListVO.setStudentId(question.getStudentId());

            questionListVO.setStudentName(questionDao.getStudentNameByStudentId(question.getStudentId()));

            questionListVO.setAttendanceId(question.getAttendanceId());

            questionListVO.setSelected(question.getSelected());

            questionListVO.setQuestionScore(question.getQuestionScore());
        }
        else {
            questionListVO=null;
        }
        return questionListVO;

    }

    /**
     * 发布提问
     * @param studentId
     * @param klassSeminarId
     * @param attendanceId
     * @return
     */
    public boolean postQuestion(BigInteger studentId,BigInteger klassSeminarId,BigInteger attendanceId) throws org.apache.ibatis.javassist.NotFoundException {
        if(questionDao.getTeamIdByStudentId(studentId)==presentationDao.getAttendanceByAttendanceId(attendanceId).getTeamId()) {
            return false;
        }

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
     * 给提问打分
     * @param questionId
     * @param questionScore
     * @return
     */
    public boolean updateQuestionScore(BigInteger questionId,BigInteger klassSeminarId,Double questionScore) throws NotFoundException {

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
        BigInteger roundId=seminarDao.getRoundIdBySeminarId(seminarId);

        Score roundScore=scoreCalculationDao.roundScoreCalculation(seminarScore,roundId,teamId,courseId);

        Double totalScore1=roundScore.getTotalScore();
        Double questionScore1=roundScore.getQuestionScore();
        Double presentationScore1=roundScore.getPresentationScore();
        Double reportScore1=roundScore.getReportScore();
        boolean success2=questionDao.updateRoundScoreEnd(roundId,teamId,presentationScore1,questionScore1,reportScore1,totalScore1);

        if(success==true&&success1==true&&success2==true) {
            return true;
        } else {
            return false;
        }
    }
}