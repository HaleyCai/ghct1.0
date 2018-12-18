package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Share;
import xmu.ghct.crm.mapper.ScoreMapper;
import xmu.ghct.crm.mapper.ShareMapper;

import java.math.BigInteger;
import java.util.List;

@Component
public class ShareDao {

    @Autowired
    ShareMapper shareMapper;

    public List<Share> listTeamShareMessageByCourseId(BigInteger courseId){
        List<Share> shareList=shareMapper.listTeamShareMessageByCourseId(courseId);
        if(shareList==null){
            //throw new ShareNotFindException();
        }
        return shareList;
    }

    public int deleteTeamShareByCourseIdAndShareId(BigInteger courseId,BigInteger shareId){
        return shareMapper.deleteTeamShareByCourseIdAndShareId(courseId,shareId);
    }

    public int launchTeamShareRequest(Share share){ return shareMapper.launchTeamShareRequest(share); }

    public List<Share> listSeminarShareMessageByCourseId(BigInteger courseId){
        List<Share> shareList=shareMapper.listSeminarShareMessageByCourseId(courseId);
        if(shareList==null){
            //throw new ShareNotFindException();
        }
        return shareList;
    }

    public int deleteSeminarShareByCourseIdAndShareId(BigInteger courseId,BigInteger shareId){
        return shareMapper.deleteSeminarShareByCourseIdAndShareId(courseId,shareId);
    }

    public int launchSeminarShareRequest(Share share){ return shareMapper.launchSeminarShareRequest(share); }
}

