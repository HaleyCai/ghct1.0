package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.ShareRequestVO;
import xmu.ghct.crm.VO.TeamApplicationVO;
import xmu.ghct.crm.entity.Share;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Component
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
     * 获取一个共享的信息
     * @param shareId
     * @return
     */
    Share getTeamShareByShareId(BigInteger shareId);

    Share getSeminarShareByShareId(BigInteger shareId);

    /**
     * 删除一个共享组队的信息
     * @param shareId
     * @return
     */
    int deleteTeamShareByShareId(BigInteger shareId);

    int deleteSeminarShareByShareId(BigInteger shareId);

    int deleteTeamShareInCourse(BigInteger subCourseId);

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
     * @return
     */
    int updateTeamShareStatusByShareId(BigInteger shareId,int status);

    /**
     * 修改共享讨论课的状态
     * @param shareId
     * @return
     */
    int updateSeminarShareStatusByShareId(BigInteger shareId,int status);
}
