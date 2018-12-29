package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.CourseVO;
import xmu.ghct.crm.VO.ShareTeamVO;
import xmu.ghct.crm.entity.Team;
import xmu.ghct.crm.entity.User;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Component
public interface TeamMapper {

    /**
     * 根据courseId获得队伍信息
     * @param courseId
     * @return
     */
    List<Team> listTeamInfoByCourseId(BigInteger courseId);

    /**
     * 插入小组人数限制
     * @param courseVO
     * @return
     */
    int insertTeamMemberLimit(CourseVO courseVO);

    /**
     * 获取组队人数限制
     * @return
     */
    CourseVO getTeamMemberLimit();

    /**
     * 根据teamId获取队伍下的学生ID
     * @param teamId
     * @return
     */
    List<BigInteger> getStudentIdByTeamId(BigInteger teamId);

    /**
     * @author hzm
     * 根据teamId获得队伍信息
     * @param teamId
     * @return
     */
    Team getTeamInfoByTeamId(BigInteger teamId);

    /**
     * @author cyq
     * 根据klassId获得班级下的所有teamId
     * @param klassId
     * @return
     */
    List<BigInteger> listTeamIdByKlassId(BigInteger klassId);

    /**
     * @cyq
     * 查找该课程是否共享分组，主或从都可
     * @param courseId
     * @return
     */
    List<ShareTeamVO> getShareTeamInfoByCourseId(BigInteger courseId);

    /**
     * @cyq
     * 删除team表中的记录
     * @param teamId
     * @return
     */
    int deleteTeamInfo(BigInteger teamId);

    /**
     * @cyq
     * 删除team_student表，team和student的关联关系
     * @param teamId
     * @return
     */
    int deleteStudentTeam(BigInteger teamId);

    /**
     * @cyq
     * 删除klass_team表
     * @param teamId
     * @return
     */
    int deleteKlassTeam(BigInteger teamId);

    /**
     * @cyq
     * 删team_student表，移除小组成员
     * @param teamId
     * @param studentId
     * @return
     */
    int removeTeamMember(BigInteger teamId,BigInteger studentId);

    /**
     * @author hzm
     * 根据teamId获得klassId
     * @param teamId
     * @return
     */
    BigInteger getKlassIdByTeamId(BigInteger teamId);

    /**
     * @cyq
     * 小组加成员
     * @param teamId
     * @param studentId
     * @return
     */
    int addTeamMember(BigInteger teamId,BigInteger studentId);
    /**
     * 根据teamId获得课程ID
     * @param teamId
     * @return
     */
    BigInteger getCourseIdByTeamId(BigInteger teamId);


    /**
     * @author hzm
     * 获取课程下所有队伍的id
     * @param courseId
     * @retur
     */
    List<BigInteger> listTeamIdByCourseId(BigInteger courseId);

}
