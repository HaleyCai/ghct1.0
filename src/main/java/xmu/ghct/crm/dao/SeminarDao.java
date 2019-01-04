package xmu.ghct.crm.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ghct.crm.VO.SeminarVO;
import xmu.ghct.crm.entity.Seminar;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.mapper.SeminarMapper;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.BitSet;
import java.util.Date;
import java.util.List;

@Component
public class SeminarDao {
    @Autowired
    SeminarMapper seminarMapper;

    public int creatSeminar(Seminar seminar) throws SQLException {
        Seminar seminar1=seminarMapper.getSeminarByCourseIdAndRoundId(seminar.getCourseId(),seminar.getRoundId());
        if(seminar1!=null)
        {
            throw new SQLException("存在该讨论课");
        }
        return seminarMapper.creatSeminar(seminar);
    }

    public int updateSeminarBySeminarId(Seminar seminar) throws NotFoundException {
        int count=seminarMapper.updateSeminarBySeminarId(seminar);
        if(count<=0)
        {
            throw new NotFoundException("未找到该讨论课");
        }
        return count;
    }

    public int deleteSeminarBySeminarId(BigInteger seminarId) throws NotFoundException {
        int count=seminarMapper.deleteSeminarBySeminarId(seminarId);
        if(count<=0)
        {
            throw new NotFoundException("该讨论课不在表中");
        }
        return count;
    }

    public int deleteKlassSeminarBySeminarId(BigInteger seminarId) throws NotFoundException {
        int count=seminarMapper.deleteKlassSeminarBySeminarId(seminarId);
        if(count<=0)
        {
            throw new NotFoundException("未找到klassSeminar");
        }
        return count;
    }

    public Seminar getSeminarBySeminarId(BigInteger seminarId) throws NotFoundException {
        Seminar seminar=seminarMapper.getSeminarBySeminarId(seminarId);
        if(seminar==null)
        {
            throw new NotFoundException("未找到讨论课");
        }
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
            throw new NotFoundException("未找到klassSeminar");
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
            throw new NotFoundException("未找到klassSeminar");
        }
        return seminarMapper.getKlassSeminarIdBySeminarIdAndKlassId(seminarId,klassId);
    }

    public SeminarVO getKlassSeminarByKlassSeminarId(BigInteger klassSeminarId) throws NotFoundException {
        SeminarVO seminarVO=seminarMapper.getKlassSeminarByKlassSeminarId(klassSeminarId);
        if(seminarVO==null)
        {
            throw new NotFoundException("未找到klassSeminar");
        }
        return seminarVO;
    }

    public BigInteger getKlassIdByKlassSeminarId(BigInteger klassSeminarId) throws NotFoundException {
        BigInteger flag=seminarMapper.getKlassIdByKlassSeminarId(klassSeminarId);
        if(flag==null)
        {
            throw new NotFoundException("未找到klassSeminar");
        }
        return flag;
    }

    public BigInteger getRoundIdBySeminarId(BigInteger seminarId) throws NotFoundException {
        BigInteger flag=seminarMapper.getRoundIdBySeminarId(seminarId);
        if(flag==null)
        {
            throw new NotFoundException("未找到round");
        }
        return flag;
    }

    public List<BigInteger> listSeminarIdByRoundId(BigInteger roundId) throws NotFoundException {
        List<BigInteger> list=seminarMapper.getSeminarIdByRoundId(roundId);
        if(list==null&&list.isEmpty())
        {
            throw new NotFoundException("未找到该轮下的讨论课");
        }
        return list;
    }

    public List<SeminarVO> listKlassSeminarIdByKlassId(BigInteger klassId) throws NotFoundException {
        List<SeminarVO> list=seminarMapper.listKlassSeminarIdByKlassId(klassId);
        if(list==null&&list.isEmpty())
        {
            throw new NotFoundException("未找到该班级下的klassSeminar");
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
