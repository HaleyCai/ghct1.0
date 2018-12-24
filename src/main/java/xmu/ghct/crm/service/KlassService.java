package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.KlassDao;
import xmu.ghct.crm.dao.RoundDao;
import xmu.ghct.crm.entity.Klass;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class KlassService {

    @Autowired
    KlassDao klassDao;
    @Autowired
    RoundDao roundDao;

    public List<Klass> listKlassByCourseId(BigInteger courseId){
        return klassDao.listKlassByCourseId(courseId);
    }

    public int creatKlass(BigInteger courseId, Map<String,Object> klassMap)  {
        Klass klass=new Klass();
        klass.setCourseId(new BigInteger(courseId.toString()));
        klass.setGrade(new Integer(klassMap.get("grade").toString()));
        klass.setKlassSerial(new Integer(klassMap.get("klassSerial").toString()));
        klass.setKlassTime(klassMap.get("klassTime").toString());
        klass.setKlassLocation(klassMap.get("klassLocation").toString());
        return klassDao.creatKlass(klass);
    }

    /**
     * 删除courseId下的所有班级
     * @param courseId
     * @return
     */
    public int deleteKlassByCourseId(BigInteger courseId){
        return klassDao.deleteKlassByCourseId(courseId);
    }



    /**
     * 根据klassId删除班级
     * @param klassId
     * @return
     */
    public boolean deleteKlassByKlassId(BigInteger klassId){
        int flag=klassDao.deleteKlassByKlassId(klassId);
        if(flag>0)return true;
        else return false;
    }

    /**
     * 根据seminarId获得讨论课下所有班级ID
     * @param seminarId
     * @return
     */
    public List<BigInteger> listKlassIdBySeminarId(BigInteger seminarId){
        return klassDao.listKlassIdBySeminarId(seminarId);
    }


    /**
     * 根据seminarId获得其所属班级信息
     * @param seminarId
     * @return
     */
    public List<Klass> listKlassBySeminarId(BigInteger seminarId){
        List<BigInteger> klassIdList=klassDao.listKlassIdBySeminarId(seminarId);
        List<Klass> klassList=new ArrayList<>();
        for(BigInteger item:klassIdList){
            Klass klass =klassDao.getKlassByKlassId(item);
            klassList.add(klass);
        }
        return klassList;
    }


}
