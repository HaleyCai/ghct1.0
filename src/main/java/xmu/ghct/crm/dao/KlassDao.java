package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Klass;
import xmu.ghct.crm.mapper.KlassMapper;

import java.math.BigInteger;
import java.util.List;

@Component
public class KlassDao {
    @Autowired
    KlassMapper klassMapper;

    public List<Klass> listKlassByCourseId(BigInteger courseId) {
        List<Klass> klassList = klassMapper.listKlassByCourseId(courseId);
        if (klassList == null) {
            //throw new NoStudentNotFindException();
        }
        return klassList;
    }

    public int creatKlass(Klass klass) {
        return klassMapper.creatKlass(klass);
    }

    public int deleteKlassByCourseId(BigInteger courseId) {
        return klassMapper.deleteKlassByCourseId(courseId);
    }

    public int deleteKlassByKlassId(BigInteger klassId){
        return klassMapper.deleteKlassByKlassId(klassId);
    }

    public Klass getKlassByKlassId(BigInteger klassId){
        return klassMapper.getKlassByKlassId(klassId);
    }

    public List<BigInteger> listKlassIdBySeminarId(BigInteger seminarId){
        return klassMapper.listKlassIdBySeminarId(seminarId);
    }

    public List<Integer> listStatusByKlassId(BigInteger klassId){
        return klassMapper.listStatusByKlassId(klassId);
    }

    public int getKlassSerialByKlassId(BigInteger klassId){
        return klassMapper.getKlassSerialByKlassId(klassId);
    }
}
