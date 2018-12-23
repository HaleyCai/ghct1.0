package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.RequestDao;
import xmu.ghct.crm.entity.Share;

import java.util.List;
import java.math.BigInteger;

@Service
public class RequestService {

    @Autowired RequestDao requestDao;

    /**
     * 获得共享申请信息列表
     * @param type
     * @param status
     * @return
     */
    public List<Share> getAllShare(int type,int status)
    {
        List<Share> result;
        if(type==1) /**teamShare**/
            result=requestDao.getAllTeamShare(status);
        else
            result=requestDao.getAllSeminarShare(status);
        return result;
    }

    /**
     * 获得某一个共享申请信息
     * @param type
     * @param shareId
     * @param status
     * @return
     */
    public Share getShare(int type,BigInteger shareId,int status)
    {
        Share result;
        if(type==1)
            result=requestDao.getTeamShare(shareId,status);
        else
            result=requestDao.getSeminarShare(shareId,status);
        return result;
    }

    /**
     * 修改共享请求状态
     * @param type
     * @param shareId
     * @param status
     * @return
     */
    public boolean updateShareStatus(int type,BigInteger shareId,int status)
    {
        int success;
        if(type==1)
            success=requestDao.updateTeamShareStatus(shareId,status);
        else
            success=requestDao.updateSeminarShareStatus(shareId,status);
        if(success==1)
            return true;
        else
            return false;
    }

}