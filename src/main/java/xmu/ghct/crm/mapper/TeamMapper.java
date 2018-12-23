package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.CourseVO;
import xmu.ghct.crm.entity.Team;
import xmu.ghct.crm.entity.User;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Component
public interface TeamMapper {

    /**
     * 根据courseId获得teamId
     * @param courseId
     * @return
     */
    List<BigInteger> getTeamIdByCourseId(BigInteger courseId);

    /**
     * 根据courseId获得队伍信息
     * @param courseId
     * @return
     */
    List<Team> listTeamInfoByCourseId(BigInteger courseId);

    /**
     * 根据courseId获得未组队学生
     * @param courseId
     * @return
     */
    List<User> getNoTeamStudentByCourseId(BigInteger courseId);

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
}
