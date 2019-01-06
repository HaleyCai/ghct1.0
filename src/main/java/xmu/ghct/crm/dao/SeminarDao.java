package xmu.ghct.crm.dao;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIGlobalBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.vo.SeminarVO;
import xmu.ghct.crm.entity.Seminar;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.mapper.SeminarMapper;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author hzm
 */
@Component
public class SeminarDao {
    @Autowired
    SeminarMapper seminarMapper;

    public int creatSeminar(Seminar seminar) throws SQLException {
        List<Seminar> seminarList=seminarMapper.getSeminarByCourseIdAndRoundId(seminar.getCourseId(),seminar.getRoundId());
        if(seminarList!=null&&seminarList.size()>0){
            for(Seminar seminar1:seminarList){
                if(seminar1.getSeminarName().equals(seminar.getSeminarName()))
                {
                    System.out.println("存在该讨论课");
                    return 0;
                }
            }
        }
        return seminarMapper.creatSeminar(seminar);
    }

    public int createNewSeminarSeminarSerial(BigInteger courseId)
    {
        List<Integer> seminarSerials=seminarMapper.listSeminarSerial(courseId);
        int max=0;
        if(seminarSerials!=null) {
            for(int serial:seminarSerials) {
                if(serial>max) {
                    max=serial;
                }
            }
        }
        return max+1;
    }

    public int updateSeminarBySeminarId(Seminar seminar){
        int count=seminarMapper.updateSeminarBySeminarId(seminar);
        return count;
    }

    public int deleteSeminarBySeminarId(BigInteger seminarId){
        int count=seminarMapper.deleteSeminarBySeminarId(seminarId);
        return count;
    }

    public int deleteKlassSeminarBySeminarId(BigInteger seminarId){
        int count=seminarMapper.deleteKlassSeminarBySeminarId(seminarId);
        return count;
    }

    public Seminar getSeminarBySeminarId(BigInteger seminarId){
        Seminar seminar=seminarMapper.getSeminarBySeminarId(seminarId);
        return seminar;
    }

    public int updateKlassSeminarByKlassSeminarId(BigInteger klassSeminarId, Date reportDDL) throws NotFoundException {
        int count=seminarMapper.updateKlassSeminarByKlassSeminarId(klassSeminarId,reportDDL);
        if(count<=0)
        {
            throw new NotFoundException("未找到klassSeminar");
        }
        return count;
    }

    public int deleteKlassSeminarBySeminarIdAndKlassId(BigInteger klassId,BigInteger seminarId) throws NotFoundException {
        int count=seminarMapper.deleteKlassSeminarBySeminarIdAndKlassId(klassId,seminarId);
        if(count<=0)
        {
            throw new NotFoundException("未找到klassSeminar");
        }
        return count;
    }

    public SeminarVO getKlassSeminarByKlassIdAndSeminarId(BigInteger klassId,BigInteger seminarId) throws NotFoundException {
        System.out.println("klassId=="+klassId);
        System.out.println("seminarId=="+seminarId);
        SeminarVO seminarVO=seminarMapper.getKlassSeminarByKlassIdAndSeminarId(klassId,seminarId);
        System.out.println(("seminarVO=="+seminarVO));
        if(seminarVO==null)
        {
            return null;
        }
        return seminarVO;
    }

    public int updateKlassSeminarStatus(BigInteger klassSeminarId,int status) throws NotFoundException {
        int count=seminarMapper.updateKlassSeminarStatus(klassSeminarId,status);
        if(count<=0)
        {
            throw new NotFoundException("未找到klassSeminar");
        }
        return count;
    }


    public  BigInteger getKlassSeminarIdBySeminarIdAndKlassId(BigInteger seminarId,BigInteger klassId) throws NotFoundException {
        System.out.println("seminarId"+seminarId+" klassId"+klassId);
        if(seminarMapper.getKlassSeminarByKlassIdAndSeminarId(klassId,seminarId)==null)
        {
            return null;
        }
        return seminarMapper.getKlassSeminarIdBySeminarIdAndKlassId(seminarId,klassId);
    }

    public SeminarVO getKlassSeminarByKlassSeminarId(BigInteger klassSeminarId) throws NotFoundException {
        SeminarVO seminarVO=seminarMapper.getKlassSeminarByKlassSeminarId(klassSeminarId);
        if(seminarVO==null)
        {
            return null;
        }
        return seminarVO;
    }

    public BigInteger getKlassIdByKlassSeminarId(BigInteger klassSeminarId) throws NotFoundException {
        BigInteger flag=seminarMapper.getKlassIdByKlassSeminarId(klassSeminarId);
        if(flag==null)
        {
            return null;
        }
        return flag;
    }

    public BigInteger getRoundIdBySeminarId(BigInteger seminarId) throws NotFoundException {
        BigInteger flag=seminarMapper.getRoundIdBySeminarId(seminarId);
        if(flag==null)
        {
            return null;
        }
        return flag;
    }

    public List<BigInteger> listSeminarIdByRoundId(BigInteger roundId) throws NotFoundException {
        List<BigInteger> list=seminarMapper.getSeminarIdByRoundId(roundId);
        if(list==null&&list.isEmpty())
        {
            return null;
        }
        return list;
    }

    public List<SeminarVO> listKlassSeminarIdByKlassId(BigInteger klassId) throws NotFoundException {
        List<SeminarVO> list=seminarMapper.listKlassSeminarIdByKlassId(klassId);
        if(list==null&&list.isEmpty())
        {
            return null;
        }
        return list;
    }

    public int insertKlassSeminarBySeminarIdAndKlassId(BigInteger seminarId,BigInteger klassId) throws SQLException {
        if(seminarMapper.getKlassSeminarByKlassIdAndSeminarId(klassId,seminarId)!=null)
        {
            throw new SQLException("已存在该klass和seminar的对应关系");
        }
        return seminarMapper.insertKlassSeminarBySeminarIdAndKlassId(seminarId,klassId);
    }

    public List<BigInteger>listKlassIdBySeminarId(BigInteger seminarId){
        return seminarMapper.listKlassIdBySeminarId(seminarId);
    }

}
