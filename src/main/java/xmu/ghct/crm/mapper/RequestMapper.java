package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Share;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Component

public interface RequestMapper {

    /**
     * 获得队伍共享申请信息列表
     * @param status
     * @return
     */
    List<Share> getAllTeamShare(int status);

    /**
     * 获得讨论课共享申请信息列表
     * @param status
     * @return
     */
    List<Share> getAllSeminarShare(int status);

    /**
     * 获得某一个队伍共享申请信息
     * @param teamShareId
     * @param status
     * @return
     */
    Share getTeamShare(BigInteger teamShareId,int status);

    /**
     * 获得某一个讨论课共享申请信息
     * @param seminarShareId
     * @param status
     * @return
     */
    Share getSeminarShare(BigInteger seminarShareId,int status);

    /**
     * 修改共享队伍请求状态
     * @param teamShareId
     * @param newStatus
     * @return
     */
    int updateTeamShareStatus(BigInteger teamShareId,int newStatus);

    /**
     * 修改共享讨论课请求状态
     * @param seminarShareId
     * @param newStatus
     * @return
     */
    int updateSeminarShareStatus(BigInteger seminarShareId,int newStatus);


}
