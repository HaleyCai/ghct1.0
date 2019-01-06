package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import xmu.ghct.crm.vo.TeamApplicationVO;
import xmu.ghct.crm.entity.Share;

import java.math.BigInteger;
import java.util.List;

/**
 * @author caiyq
 */
@Mapper
@Repository
public interface ShareMapper {

    /**
     * 获得某状态的，全部team共享信息
     * @param courseId
     * @return
     */
    List<Share> getAllTeamShare(BigInteger courseId);

    /**
     * 获得某状态的，全部courseId共享信息
     * @param courseId
     * @return
     */
    List<Share> getAllSeminarShare(BigInteger courseId);

    /**
     * 检查该课程是否已经成为其他课的组队从课程
     * @param courseId
     * @return
     */
    Share getSubTeamShare(BigInteger courseId);

    /**
     * 检查该课程是否已经成为其他课的讨论课从课程
     * @param courseId
     * @return
     */
    Share getSubSeminarShare(BigInteger courseId);

    /**
     * 获取待办的共享组队请求
     * @param subCourseId
     * @return
     */
    List<Share> getTeamShareRequest(BigInteger subCourseId);

    /**
     * 获取待办的共享讨论课
     * @param subCourseId
     * @return
     */
    List<Share> getSeminarShareRequest(BigInteger subCourseId);

    /**
     * 获取一个共享组队的信息
     * @param shareId
     * @return
     */
    Share getTeamShareByShareId(BigInteger shareId);

    /**
     * 获取一个共享讨论课的信息
     * @param shareId
     * @return
     */
    Share getSeminarShareByShareId(BigInteger shareId);

    /**
     * 删除一个共享组队的信息
     * @param shareId
     * @return
     */
    int deleteTeamShareByShareId(BigInteger shareId);

    /**
     * 删除一个共享讨论课的信息
     * @param shareId
     * @return
     */
    int deleteSeminarShareByShareId(BigInteger shareId);

    /**
     * 删除从课程中的共享组队主课程
     * @param subCourseId
     * @return
     */
    int deleteTeamShareInCourse(BigInteger subCourseId);

    /**
     * 删除从课程中的共享讨论课主课程
     * @param subCourseId
     * @return
     */
    int deleteSeminarShareInCourse(BigInteger subCourseId);

    /**
     * 获取发送给某教师的非法组队申请
     * @param teacherId
     * @return
     */
    List<TeamApplicationVO> getUntreatedTeamApplication(BigInteger teacherId);

    /**
     * 获取一个非法组队申请的信息
     * @param teamValidId
     * @return
     */
    TeamApplicationVO getOneTeamApplication(BigInteger teamValidId);

    /**
     * 修改非法组队申请的状态
     * @param teamValidId
     * @param status
     * @return
     */
    int dealTeamValidRequest(BigInteger teamValidId,int status);

    /**
     * 发送共享组队请求
     * @param share
     * @return
     */
    int launchTeamShareRequest(Share share);

    /**
     * 发送共享讨论课请求
     * @param share
     * @return
     */
    int launchSeminarShareRequest(Share share);

    /**
     * 发送非法组队申请
     * @param applicationVO
     * @return
     */
    int launchTeamRequest(TeamApplicationVO applicationVO);

    /**
     * 修改共享组队的状态
     * @param shareId
     * @param status
     * @return
     */
    int updateTeamShareStatusByShareId(BigInteger shareId,int status);

    /**
     * 修改共享讨论课的状态
     * @param shareId
     * @param status
     * @return
     */
    int updateSeminarShareStatusByShareId(BigInteger shareId,int status);
}
