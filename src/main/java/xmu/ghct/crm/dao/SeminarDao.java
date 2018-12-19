package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.entity.Seminar;
import xmu.ghct.crm.mapper.SeminarMapper;

@Component
public class SeminarDao {
    @Autowired
    SeminarMapper seminarMapper;

    public int creatSeminar(Seminar seminar){
        return seminarMapper.creatSeminar(seminar);
    }
}
