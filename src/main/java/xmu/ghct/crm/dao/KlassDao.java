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

    public int deleteKlassByCourseIdAndKlassId(BigInteger courseId, BigInteger klassId) {
        return klassMapper.deleteKlassByCourseIdAndKlassId(courseId, klassId);
    }

}
