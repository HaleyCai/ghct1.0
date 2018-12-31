package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.AndOrOrStrategyVO;
import xmu.ghct.crm.VO.CourseLimitVO;
import xmu.ghct.crm.VO.CourseVO;
import xmu.ghct.crm.VO.TeamStrategyVO;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Component
public interface StrategyMapper {

    /**
     * 获取或表最大ID值
     * @return
     */
    BigInteger selectMaxIdFromTeamOrStrategy();

    /**
     * 获取与表最大ID值
     * @return
     */
    BigInteger selectMaxIdFromTeamAndStrategy();

    /**
     * 获取课程人数限制表最大ID值
     * @return
     */
    BigInteger selectMaxIdFromConflictCourseStrategy();

    /**
     * 插入课程队伍人数限制
     * @param courseVO
     * @return
     */
    int insertMemberLimit(CourseVO courseVO);

    /**
     * 插入队伍选某课程人数的要求
     * @param courseLimitVO
     * @return
     */
    int insertCourseMemberLimit(CourseLimitVO courseLimitVO);

    /**
     * 插入与表
     * @param id
     * @param strategyName
     * @param strategyId
     * @return
     */
    int insertAndStrategy(BigInteger id,String strategyName,BigInteger strategyId);

    /**
     * 插入或表
     * @param id
     * @param strategyName
     * @param strategyId
     * @return
     */
    int insertOrStrategy(BigInteger id,String strategyName,BigInteger strategyId);

    /**
     * 添加组队策略
     * @param courseId
     * @param strategySerial
     * @param strategyId
     * @param strategyName
     * @return
     */
    int insertTeamStrategy(BigInteger courseId,Integer strategySerial,BigInteger strategyId,String strategyName);

    /**
     * 插入冲突课程
     * @param id
     * @param courseId
     * @return
     */
    int insertIntoConflictCourseStrategy(BigInteger id,BigInteger courseId);

    /**
     * 获取某课程的组队策略
     * @param courseId
     * @return
     */
    List<TeamStrategyVO> listTeamStrategyByCourseId(BigInteger courseId);

    /**
     * 获取队伍人数
     * @param teamId
     * @return
     */
    int getTeamMemberNumber(BigInteger teamId);

    /**
     * 获取队伍人数限制
     * @param memberLimitId
     * @return
     */
    CourseVO getTeamMemberLimit(BigInteger memberLimitId);

    /**
     * 获取“与”策略
     * @param strategyId
     * @return
     */
    List<AndOrOrStrategyVO> selectAndStrategy(BigInteger strategyId);

    /**
     * 获取“或”策略
     * @param strategyId
     * @return
     */
    List<AndOrOrStrategyVO> selectOrStrategy(BigInteger strategyId);

    /**
     * 获得课程人数限制（本课程组队对其他课程人数的限制）
     * @param strategyId
     * @return
     */
    CourseLimitVO getCourseLimitByStrategyId(BigInteger strategyId);

    /**
     * 返回学生是否选课的数值
     * @param courseId
     * @param studentId
     * @return
     */
    int getCourseStudentNumber(BigInteger courseId,BigInteger studentId);

    /**
     * 获取某队伍下所有学生ID
     * @param teamId
     * @return
     */
    List<BigInteger> listStudentIdByTeamId(BigInteger teamId);

    /**
     * 获取冲突课程ID
     * @param strategyId
     * @return
     */
    List<BigInteger> listConflictCourseId(BigInteger strategyId);
}
