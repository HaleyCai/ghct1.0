package xmu.ghct.crm.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.mapper.RequestMapper;
import xmu.ghct.crm.entity.Share;
import java.math.BigInteger;
import java.util.List;

@Component
public class RequestDao {
    @Autowired RequestMapper requestMapper;
    /**
     * 获得队伍共享申请信息列表
     * @param status
     * @return
     */
    public List<Share> getAllTeamShare(int status)
    {
        return requestMapper.getAllTeamShare(status);
    }

    /**
     * 获得讨论课共享申请信息列表
     * @param status
     * @return
     */
    public List<Share> getAllSeminarShare(int status)
    {
        return requestMapper.getAllSeminarShare(status);
    }

    /**
     * 获得某一个队伍共享申请信息
     * @param teamShareId
     * @param status
     * @return
     */
    public Share getTeamShare(BigInteger teamShareId,int status)
    {
        return requestMapper.getTeamShare(teamShareId,status);
    }

    /**
     * 获得某一个讨论课共享申请信息
     * @param seminarShareId
     * @param status
     * @return
     */
    public Share getSeminarShare(BigInteger seminarShareId,int status)
    {
        return requestMapper.getSeminarShare(seminarShareId,status);
    }

    /**
     * 修改共享队伍请求状态
     * @param teamShareId
     * @param status
     * @return
     */
    public int updateTeamShareStatus(BigInteger teamShareId,int status)
    {
        return requestMapper.updateTeamShareStatus(teamShareId,status);
    }

    /**
     * 修改共享讨论课请求状态
     * @param seminarShareId
     * @param status
     * @return
     */
    public int updateSeminarShareStatus(BigInteger seminarShareId,int status)
    {
        return requestMapper.updateSeminarShareStatus(seminarShareId,status);
    }
}
