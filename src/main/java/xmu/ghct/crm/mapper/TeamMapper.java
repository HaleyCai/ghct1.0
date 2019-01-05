package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
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

    /**
     * @author hzm
     * 获取队伍序号
     * @param teamId
     * @return
     */
    Integer getTeamSerialByTeamId(BigInteger teamId);

    /**
     * 获取班级下最大队伍序号
     * @param klassId
     * @return
     */
    Integer getMaxTeamSerialOfTeam(BigInteger klassId);


    /**
     * 创建队伍
     * @param team
     * @return
     */
    int insertTeam(Team team);

    /**
     * 更新队伍合法状态
     * @param teamId
     * @param status
     * @return
     */
    int updateStatusByTeamId(BigInteger teamId,int status);

    /**
     * 插入班级-队伍关联信息
     * @param klassId
     * @param teamId
     * @return
     */
    int insertKlassTeam(BigInteger klassId,BigInteger teamId);

    /**
     * 插入学生-队伍关联信息
     * @param teamId
     * @param studentId
     * @return
     */
    int insertTeamStudent(BigInteger teamId,BigInteger studentId);


    /**
     * 插入学生-班级-课程关联信息
     * @param klassId
     * @param studentId
     * @param courseId
     * @return
     */
    int insertKlassStudent(BigInteger klassId,BigInteger studentId,BigInteger courseId);

    /**
     * 获取学生所在队伍ID
     * @param studentId
     * @return
     */
    List<BigInteger> listTeamIdByStudentId(BigInteger studentId);

    /**
     * 获取队伍所在所有班级ID
     * @param teamId
     * @return
     */
    List<BigInteger> listKlassIdByTeamId(BigInteger teamId);

}
