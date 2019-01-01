package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.RoundEnrollVO;
import xmu.ghct.crm.VO.RoundVO;
import xmu.ghct.crm.VO.ScoreVO;
import xmu.ghct.crm.VO.SeminarSimpleVO;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.entity.Round;
import xmu.ghct.crm.entity.Seminar;
import xmu.ghct.crm.mapper.KlassMapper;
import xmu.ghct.crm.mapper.RoundMapper;
import xmu.ghct.crm.mapper.ScoreMapper;
import xmu.ghct.crm.mapper.TeamMapper;

import java.math.BigInteger;
import java.util.*;

/**
 * @author caiyq
 */
@Component
public class RoundDao {

    @Autowired
    private RoundMapper roundMapper;
    @Autowired
    private KlassMapper klassMapper;

    @Autowired
    TeamMapper teamMapper;

    @Autowired
    ScoreMapper scoreMapper;
    /**
     * @cyq
     * 根据roundId获取该轮次下所有seminar的简单信息
     * @param roundId
     * @return
     */
    public List<SeminarSimpleVO> getSeminarByRoundId(BigInteger roundId){
        List<Seminar> list=roundMapper.getSeminarByRoundId(roundId);
        List<SeminarSimpleVO> simpleList=new ArrayList<>();
        for(Seminar item:list)
        {
            SeminarSimpleVO temp=new SeminarSimpleVO();
            temp.setId(item.getSeminarId());
            temp.setTopic(item.getSeminarName());
            temp.setOrder(item.getSeminarSerial());
            simpleList.add(temp);
        }
        if(list==null){
            //throw
        }
        return simpleList;
    }

    /**
     * @cyq
     * 将表示成绩评定方式的数字转为字符串
     * @param i
     * @return
     */
    private String intToString(int i)
    {
        if(i==0)
            return "平均分";
        else
            return "最高分";
    }

    /**
     * 将表示成绩评定方式的字符串转为数字
     * @param s
     * @return
     */
    private int stringToInt(String s)
    {
        if(s.equals("平均分"))
            return 0;
        else
            return 1;
    }

    /**
     * @cyq
     * 根据courseId和roundId获得轮次信息
     * @param courseId
     * @param roundId
     * @return
     */
    public RoundVO getRoundByRoundId(BigInteger courseId,BigInteger roundId){
        Round round=roundMapper.getRoundByRoundId(roundId);
        if(round==null){
            //throw
        }
        RoundVO roundVO=new RoundVO();
        roundVO.setRoundId(round.getRoundId());
        roundVO.setRoundSerial(round.getRoundSerial());
        //将Int转为String
        roundVO.setPresentationScoreMethod(intToString(round.getPresentationScoreMethod()));
        roundVO.setReportScoreMethod(intToString(round.getReportScoreMethod()));
        roundVO.setQuestionScoreMethod(intToString(round.getQuestionScoreMethod()));
        //查找round下各班的最大报名组数
        roundVO.setEnrollNum(getKlassEnrollNum(courseId,roundId));
        return roundVO;
    }

    /**
     * 根据课程id查其下所有的klassId，结合klassId和roundId查该轮次该班的最大报名次数
     * @param courseId
     * @param roundId
     * @return
     */
    public List<RoundEnrollVO> getKlassEnrollNum(BigInteger courseId,BigInteger roundId)
    {
        //查round下所有的klass
        List<Klass> klassList=klassMapper.listKlassByCourseId(courseId);
        List<RoundEnrollVO> enrollNum=new ArrayList<>();
        for(Klass item:klassList)
        {
            RoundEnrollVO enrollVO=new RoundEnrollVO();
            enrollVO.setKlassId(item.getKlassId());
            enrollVO.setKlassSerial(item.getKlassSerial());
            enrollVO.setGrade(item.getGrade());
            //最大允许报名次数
            Integer max=roundMapper.getEnrollNum(enrollVO.getKlassId(),roundId);
            if(max==null)
                enrollVO.setEnroll(0);
            else
                enrollVO.setEnroll(max);
            enrollNum.add(enrollVO);
        }
        return enrollNum;
    }

    /**
     * 根据roundId修改轮次信息（成绩评定方式和各班下的最大报名次数）
     * @param roundVO
     * @return
     */
    public boolean modifyRoundByRoundId(RoundVO roundVO){
        //修改成绩评定方式
        roundMapper.modifyRoundByRoundId(roundVO.getRoundId(),
                roundVO.getCourseId(),
                stringToInt(roundVO.getPresentationScoreMethod()),
                stringToInt(roundVO.getReportScoreMethod()),
                stringToInt(roundVO.getQuestionScoreMethod()));
        //修改各班下的最大报名次数
        System.out.println("roundVO.getEnrollNum().class==="+roundVO.getEnrollNum().getClass());
        for(RoundEnrollVO roundEnrollVO:roundVO.getEnrollNum())
        {
            roundMapper.modifyEnrollNum(
                    roundEnrollVO.getKlassId(),
                    roundVO.getRoundId(),
                    roundEnrollVO.getEnroll());
        }
        return true;
    }

    /**
     * 根据courseId获取该课程下所有轮次
     * @param courseId
     * @return
     */
    public List<Round> listRoundByCourseId(BigInteger courseId){
        return roundMapper.listRoundByCourseId(courseId);
    }

    /**
     * 创建轮次
     * @param round
     * @return
     */
    public int insertRound(Round round,BigInteger courseId){
        int flag= roundMapper.insertRound(round);
        List<BigInteger> teamIdList=teamMapper.listTeamIdByCourseId(courseId);
        for(BigInteger teamId:teamIdList){
            ScoreVO scoreVO=new ScoreVO();
            scoreVO.setTeamId(teamId);
            scoreVO.setRoundId(round.getRoundId());
            scoreMapper.insertRoundScore(scoreVO);
        }
        return flag;
    }

    /**
     * @cyq
     * 创建默认讨论课的序号（查找一个课程下，轮次的最大值，+1）
     * @param courseId
     * @return
     */
    public int getNewRoundNum(BigInteger courseId)
    {
        return roundMapper.getRoundNumByCourseId(courseId)+1;//默认值为最大值加一
    }

    /**
     * @cyq
     * 默认创建每个班该轮允许报名次数为1
     * @param courseId
     * @param roundId
     * @return
     */
    public void defaultEnrollNumber(BigInteger courseId,BigInteger roundId)
    {
        //查class有几个，每个class调一遍创建
        List<Klass> klassList=klassMapper.listKlassByCourseId(courseId);
        for(Klass item:klassList)
            roundMapper.createDefaultEnrollNumber(item.getKlassId(),roundId);
    }

    /**
     * @author hzm
     * 获取轮次序号
     * @param roundId
     * @return
     */
    public int getRoundSerialByRoundId(BigInteger roundId){
        return  roundMapper.getRoundSerialByRoundId(roundId);
    }


}
