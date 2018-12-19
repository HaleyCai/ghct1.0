package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Share;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Component
public interface ShareMapper {

    /**
     * 根据courseId获得共享队伍信息
     * @param courseId
     * @return
     */
    List<Share> listTeamShareMessageByCourseId(BigInteger courseId);

    /**
     * 根据courseId和shareId删除共享队伍信息
     * @param courseId
     * @param shareId
     * @return
     */
    int deleteTeamShareByCourseIdAndShareId(BigInteger courseId,BigInteger shareId);

    /**
     * 发布共享队伍请求
     * @param share
     * @return
     */
    int launchTeamShareRequest(Share share);

    /**
     * 根据courseId获得讨论可共享信息
     * @param courseId
     * @return
     */
    List<Share> listSeminarShareMessageByCourseId(BigInteger courseId);

    /**
     * 根据courseId和shareId删除共享讨论课信息
     * @param courseId
     * @param shareId
     * @return
     */
    int deleteSeminarShareByCourseIdAndShareId(BigInteger courseId,BigInteger shareId);

    /**
     * 发布共享讨论课请求
     * @param share
     * @return
     */
    int launchSeminarShareRequest(Share share);
}
