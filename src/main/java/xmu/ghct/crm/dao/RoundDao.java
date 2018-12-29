package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
    public Map<String,Integer> getKlassEnrollNum(BigInteger courseId,BigInteger roundId)
    {
        Map<String,Integer> result=new TreeMap<>();
        //查round下所有的klass
        List<Klass> klassList=klassMapper.listKlassByCourseId(courseId);
        System.out.println(klassList);
        for(Klass item:klassList)
        {
            String klassName=item.getGrade()+" "+String.valueOf(item.getKlassSerial());//班级名字是由年级+班级序号
            //查表找报名次数
            int times=roundMapper.getEnrollNum(item.getKlassId(),roundId);
            result.put(klassName,times);
        }
        return result;
    }

    /**
     * 根据roundId修改轮次信息（成绩评定方式和各班下的最大报名次数）
     * @param roundVO
     * @return
     */
    public boolean modifyRoundByRoundId(RoundVO roundVO){
        //修改成绩评定方式
        roundMapper.modifyRoundByRoundId(roundVO.getRoundId(),
                stringToInt(roundVO.getPresentationScoreMethod()),
                stringToInt(roundVO.getReportScoreMethod()),
                stringToInt(roundVO.getQuestionScoreMethod()));
        //将map变为klassId和num，传roundId
        Map<BigInteger,Integer> enroll=new TreeMap<>();
        for(String klassName:roundVO.getEnrollNum().keySet())
        {
            String s[]=klassName.split(" ");
            int grade=Integer.parseInt(s[0]);
            int klassSerial=Integer.parseInt(s[1]);
            BigInteger klassId=klassMapper.getKlassByGradeAndKlassSerial(grade,klassSerial).getKlassId();
            enroll.put(klassId,roundVO.getEnrollNum().get(klassSerial));
        }
        //修改各班下的最大报名次数
        for(BigInteger klassId:enroll.keySet())
        {
            roundMapper.modifyEnrollNum(klassId,roundVO.getRoundId(),enroll.get(klassId));
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
