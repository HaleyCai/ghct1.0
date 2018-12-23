package xmu.ghct.crm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Share;
import xmu.ghct.crm.VO.TeamApplicationVO;
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
     * @param status
     * @return
     */
    int updateTeamShareStatus(BigInteger teamShareId,int status);

    /**
     * 修改共享讨论课请求状态
     * @param seminarShareId
     * @param status
     * @return
     */
    int updateSeminarShareStatus(BigInteger seminarShareId,int status);

    /**
     * 获得组队申请信息列表
     * @param status
     * @return
     */
    List<TeamApplicationVO> getAllTeamApplication(int status);

    /**
     * 获得某一个组队申请信息
     * @param teamValidId
     * @param status
     * @return
     */
    TeamApplicationVO getTeamApplication(BigInteger teamValidId,int status);

    /**
     * 修改组队请求状态
     * @param teamValidId
     * @param status
     * @return
     */
    int updateTeamApplicationStatus(BigInteger teamValidId,int status);

}
