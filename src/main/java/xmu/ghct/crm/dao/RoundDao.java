package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.RoundVO;
import xmu.ghct.crm.VO.SeminarSimpleVO;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.entity.Round;
import xmu.ghct.crm.entity.Seminar;
import xmu.ghct.crm.mapper.KlassMapper;
import xmu.ghct.crm.mapper.RoundMapper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caiyq
 */
@Component
public class RoundDao {

    @Autowired
    private RoundMapper roundMapper;
    @Autowired
    private KlassMapper klassMapper;
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
     * @cyq
     * 根据roundId获得轮次信息
     * @param roundId
     * @return
     */
    public RoundVO getRoundByRoundId(BigInteger roundId){
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
        return roundVO;
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
     * 根据roundId修改轮次信息（成绩评定方式）
     * @param roundVO
     * @return
     */
    public boolean modifyRoundByRoundId(RoundVO roundVO){
        int v1=roundMapper.modifyRoundByRoundId(roundVO.getRoundId(),
                stringToInt(roundVO.getPresentationScoreMethod()),
                stringToInt(roundVO.getReportScoreMethod()),
                stringToInt(roundVO.getQuestionScoreMethod()));
        if(v1<=0){
            //throw
            return false;
        }
        else
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
    public int insertRound(Round round){
        return roundMapper.insertRound(round);
    }

    /**
     * @cyq
     * 查找一个课程下，轮次的最大值，用来创建默认讨论课的序号
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
}
