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

    /**
     * 删除班级-轮次关联
     * @param klassId
     * @return
     */
    public int deleteKlassRoundByKlassId(BigInteger klassId){
        return klassMapper.deleteKlassRoundByKlassId(klassId);
    }

    /**
     * 删除班级-学生关系
     * @param klassId
     * @return
     */
    public int deleteKlassStudentByKlassId(BigInteger klassId){
        return klassMapper.deleteKlassStudentByKlassId(klassId);
    }

    /**
     * 删除班级—讨论课关系
     * @param klassId
     * @return
     */
    public int deleteKlassSeminarByKlassId(BigInteger klassId){
        return klassMapper.deleteKlassSeminarByKlassId(klassId);
    }

    /**
     * 根据klassId删除klass_team关系
     * @param klassId
     */
    public int deleteTeamWithKlass(BigInteger klassId){
        return klassMapper.deleteTeamWithKlass(klassId);
    }

}
