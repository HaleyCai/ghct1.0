package xmu.ghct.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.ghct.crm.dao.KlassDao;
import xmu.ghct.crm.dao.RoundDao;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.exception.NotFoundException;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hzm
 */
@Service
public class KlassService {

    @Autowired
    KlassDao klassDao;
    @Autowired
    RoundDao roundDao;

    public List<Klass> listKlassByCourseId(BigInteger courseId) throws NotFoundException {
        return klassDao.listKlassByCourseId(courseId);
    }

    public int creatKlass(BigInteger courseId, Map<String,Object> klassMap) throws NotFoundException, SQLException {
        Klass klass=new Klass();
        klass.setCourseId(courseId);
        int klassSerial=new Integer(klassMap.get("klassSerial").toString());
        List<Klass> klassList=klassDao.listKlassByCourseId(courseId);
        for(Klass item:klassList){
            if(klassSerial==item.getKlassSerial()) {
                return 0;
            }
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
    public int deleteKlassByCourseId(BigInteger courseId) throws NotFoundException {
        return klassDao.deleteKlassByCourseId(courseId);
    }



    /**
     * 根据klassId删除班级
     * @param klassId
     * @return
     */
    public boolean deleteKlassByKlassId(BigInteger klassId) throws NotFoundException {
        int flag=klassDao.deleteKlassByKlassId(klassId);
        klassDao.deleteKlassRoundByKlassId(klassId);
        klassDao.deleteKlassSeminarByKlassId(klassId);
        klassDao.deleteKlassStudentByKlassId(klassId);
        klassDao.deleteTeamWithKlass(klassId);
        if(flag>0) {
            return true;
        } else {
            return false;
        }
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
            if(klass!=null) {
                klassList.add(klass);
            }
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

    /**
     * 查学生在某课程里的班级
     * @param courseId
     * @param studentId
     * @return
     */
    public BigInteger getKlassIdByCourseIdAndStudentId(BigInteger courseId,BigInteger studentId){
        return klassDao.getKlassIdByCourseIdAndStudentId(courseId,studentId);
    }

    /**
     * 获取班级学生人数
     * @param klassId
     * @return
     */
    public Integer getStudentNumber(BigInteger klassId){
        return klassDao.getStudentNumber(klassId);
    }


}
