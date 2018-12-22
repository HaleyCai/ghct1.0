package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Seminar;
import xmu.ghct.crm.mapper.SeminarMapper;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.List;

@Component
public class SeminarDao {
    @Autowired
    SeminarMapper seminarMapper;

    public int creatSeminar(Seminar seminar){
        return seminarMapper.creatSeminar(seminar);
    }

    public List<BigInteger> listKlassIdBySeminarId(BigInteger semianrId){
        return seminarMapper.listKlassIdBySeminarId(semianrId);
    }

    public int updateSeminarBySeminarId(Seminar seminar){
        return seminarMapper.updateSeminarBySeminarId(seminar);
    }

    public int deleteSeminarBySeminarId(BigInteger seminarId){
        return seminarMapper.deleteSeminarBySeminarId(seminarId);
    }

    public int deleteKlassSeminarBySeminarId(BigInteger seminarId){
        return seminarMapper.deleteKlassSeminarBySeminarId(seminarId);
    }

    public  Seminar getSeminarBySeminarId(BigInteger seminarId){
        return seminarMapper.getSeminarBySeminarId(seminarId);
    }

    public int updateKlassSeminarBySeminarIdAndKlassId(BigInteger klassId,BigInteger seminarId){
        return seminarMapper.updateKlassSeminarBySeminarIdAndKlassId(klassId,seminarId);
    }
}
