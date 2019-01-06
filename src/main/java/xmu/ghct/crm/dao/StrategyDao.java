package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.vo.AndOrOrStrategyVO;
import xmu.ghct.crm.vo.CourseLimitVO;
import xmu.ghct.crm.vo.CourseVO;
import xmu.ghct.crm.vo.TeamStrategyVO;
import xmu.ghct.crm.mapper.StrategyMapper;

import java.math.BigInteger;
import java.util.List;

/**
 * @author hzm
 */
@Component
public class StrategyDao {

    @Autowired
    StrategyMapper strategyMapper;


    /**
     * 获取或表最大ID值
     * @return
     */
    public BigInteger selectMaxIdFromTeamOrStrategy(){
        return strategyMapper.selectMaxIdFromTeamOrStrategy();
    }

    /**
     * 获取与表最大ID值
     * @return
     */
    public BigInteger selectMaxIdFromTeamAndStrategy(){
        return strategyMapper.selectMaxIdFromTeamAndStrategy();
    }

    public int insertMemberLimit(CourseVO courseVO){
        return strategyMapper.insertMemberLimit(courseVO);
    }

    public int insertCourseMemberLimit(CourseLimitVO courseLimitVO){
        return strategyMapper.insertCourseMemberLimit(courseLimitVO);
    }

    public List<TeamStrategyVO> listTeamStrategyByCourseId(BigInteger courseId){
        return strategyMapper.listTeamStrategyByCourseId(courseId);
    }


    public int getTeamMemberNumber(BigInteger teamId){
        return  strategyMapper.getTeamMemberNumber(teamId);
    }

    public CourseVO getTeamMemberLimit(BigInteger memberLimitId){
        return strategyMapper.getTeamMemberLimit(memberLimitId);
    }

    /**
     * 获取“与”策略
     * @param strategyId
     * @return
     */
    public List<AndOrOrStrategyVO> selectAndStrategy(BigInteger strategyId){
        return strategyMapper.selectAndStrategy(strategyId);
    }

    /**
     * 获取“或”策略
     * @param strategyId
     * @return
     */
    public List<AndOrOrStrategyVO> selectOrStrategy(BigInteger strategyId){
        return strategyMapper.selectOrStrategy(strategyId);
    }

    /**
     * 获得课程人数限制（本课程组队对其他课程人数的限制）
     * @param strategyId
     * @return
     */
    public CourseLimitVO getCourseLimitByStrategyId(BigInteger strategyId){
        return strategyMapper.getCourseLimitByStrategyId(strategyId);
    }

    /**
     * 返回学生是否选课的数值
     * @param courseId
     * @param studentId
     * @return
     */
    public int getCourseStudentNumber(BigInteger courseId,BigInteger studentId){
        return strategyMapper.getCourseStudentNumber(courseId,studentId);
    }

    /**
     * 获取某队伍下所有学生ID
     * @param teamId
     * @return
     */
    public  List<BigInteger> listStudentIdByTeamId(BigInteger teamId){
        return strategyMapper.listStudentIdByTeamId(teamId);
    }

    /**
     * 获取冲突课程ID
     * @param strategyId
     * @return
     */
    public  List<BigInteger> listConflictCourseId(BigInteger strategyId){
        return strategyMapper.listConflictCourseId(strategyId);
    }

    /**
     * 获取其他课程人数限制
     * @param strategyId
     * @return
     */
    public List<CourseLimitVO> listCourseMemberLimit(BigInteger strategyId){
        return strategyMapper.listCourseMemberLimit(strategyId);
    }

    /**
     * 获取其他课程人数限制策略ID
     * @param strategyId
     * @return
     */
    public List<BigInteger> listStrategyIdByStrategyId(BigInteger strategyId){
        return strategyMapper.listStrategyIdByStrategyId(strategyId);
    }

}
