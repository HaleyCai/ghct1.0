package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.ShareDao;
import xmu.ghct.crm.entity.Share;
import xmu.ghct.crm.mapper.ShareMapper;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Service
public class ShareService {
    @Autowired
    ShareDao shareDao;

    public List<Share> listTeamShareMessageByCourseId(BigInteger courseId) {
        List<Share> listShare=shareDao.listTeamShareMessageByCourseId(courseId);
        for(Share item:listShare)
            item.setShareType("teamShare");
        return listShare;
    }

    public  int  deleteTeamShareByCourseIdAndShareId(BigInteger courseId,BigInteger shareId){
        return shareDao.deleteTeamShareByCourseIdAndShareId(courseId, shareId);
    }

    public int launchTeamShareRequest(BigInteger courseId, Map<String,Object> shareMap)  {
        Share share=new Share();
        share.setShareId(new BigInteger(shareMap.get("shareId").toString()));
        share.setMainCourseId(new BigInteger(courseId.toString()));
        share.setSubCourseId(new BigInteger(shareMap.get("subCourseId").toString()));
        share.setSubCourseTeacherId(new BigInteger(shareMap.get("subCourseTeacherId").toString()));
        share.setStatus(new Boolean(shareMap.get("status").toString()));
        return shareDao.launchTeamShareRequest(share);
    }

    public List<Share> listSeminarShareMessageByCourseId(BigInteger courseId) {
        List<Share> listShare=shareDao.listSeminarShareMessageByCourseId(courseId);
        for(Share item:listShare)
            item.setShareType("seminarShare");
        return listShare;
    }

    public  int  deleteSeminarShareByCourseIdAndShareId(BigInteger courseId,BigInteger shareId){
        return shareDao.deleteSeminarShareByCourseIdAndShareId(courseId, shareId);
    }

    public int launchSeminarShareRequest( BigInteger courseId,Map<String,Object> shareMap)  {
        Share share=new Share();
        share.setShareId(new BigInteger(shareMap.get("shareId").toString()));
        share.setMainCourseId(new BigInteger(courseId.toString()));
        share.setSubCourseId(new BigInteger(shareMap.get("subCourseId").toString()));
        share.setSubCourseTeacherId(new BigInteger(shareMap.get("subCourseTeacherId").toString()));
        share.setStatus(new Boolean(shareMap.get("status").toString()));
        return shareDao.launchSeminarShareRequest(share);
    }

}
