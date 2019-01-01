package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.KlassDao;
import xmu.ghct.crm.dao.RoundDao;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.exception.NotFoundException;

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
        klass.setCourseId(courseId);
        int klassSerial=new Integer(klassMap.get("klassSerial").toString());
        List<Klass> klassList=klassDao.listKlassByCourseId(courseId);
        for(Klass item:klassList){
            if(klassSerial==item.getKlassSerial()) return 0;
        }
        klass.setGrade(new Integer(klassMap.get("grade").toString()));
        klass.setKlassSerial(klassSerial);
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
        klassDao.deleteKlassRoundByKlassId(klassId);
        klassDao.deleteKlassSeminarByKlassId(klassId);
        klassDao.deleteKlassStudentByKlassId(klassId);
        klassDao.deleteTeamWithKlass(klassId);
        if(flag>0)return true;
        else return false;
    }

    /**
     * 根据seminarId获得其所属班级信息
     * @param seminarId
     * @return
     */
    public List<Klass> listKlassBySeminarId(BigInteger seminarId) throws NotFoundException {
        List<BigInteger> klassIdList=klassDao.listKlassIdBySeminarId(seminarId);
        System.out.println("klassIdList "+klassIdList);
        List<Klass> klassList=new ArrayList<>();
        for(BigInteger item:klassIdList){
            Klass klass =klassDao.getKlassByKlassId(item);
            if(klass!=null)
                klassList.add(klass);
        }
        System.out.println("klassList: "+klassList);
        return klassList;
    }

    /**
     * @author hzm
     * 根据klassId获得班级
     * @param klassId
     * @return
     */
    public Klass getKlassByKlassId(BigInteger klassId) throws NotFoundException {
        return klassDao.getKlassByKlassId(klassId);
    }


}
