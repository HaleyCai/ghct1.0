package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.KlassDao;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.entity.Seminar;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Service
public class KlassService {

    @Autowired
    KlassDao klassDao;

    public List<Klass> listKlassByCourseId(BigInteger courseId){
        return klassDao.listKlassByCourseId(courseId);
    }

    public int creatKlass(BigInteger courseId, Map<String,Object> klassMap)  {
        Klass klass=new Klass();
        klass.setKlassId(new BigInteger(klassMap.get("klassId").toString()));
        klass.setCourseId(new BigInteger(courseId.toString()));
        klass.setGrade(new Integer(klassMap.get("grade").toString()));
        klass.setKlassSerial(new Integer(klassMap.get("klassSerial").toString()));
        klass.setKlassTime(klassMap.get("klassTime").toString());
        klass.setKlassLocation(klassMap.get("klassLocation").toString());
        return klassDao.creatKlass(klass);
    }

    public int deleteKlassByCourseIdAndKlassId(BigInteger courseId,BigInteger klassId){
        return klassDao.deleteKlassByCourseIdAndKlassId(courseId, klassId);
    }

}
