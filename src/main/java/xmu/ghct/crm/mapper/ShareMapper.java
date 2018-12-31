package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.ShareRequestVO;
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
     * @param teacherId
     * @return
     */
    List<Share> getTeamShareRequest(BigInteger teacherId);

    /**
     * 获取待办的共享讨论课
     * @param teacherId
     * @return
     */
    List<Share> getSeminarShareRequest(BigInteger teacherId);

    Share getTeamShareByShareId(BigInteger shareId);

    Share getSeminarShareByShareId(BigInteger shareId);



    int deleteTeamShareByShareId(BigInteger shareId);

    int deleteSeminarShareByShareId(BigInteger shareId);
    /**
     * 发布共享讨论课请求
     * @param share
     * @return
     */
    int launchSeminarShareRequest(Share share);
}
